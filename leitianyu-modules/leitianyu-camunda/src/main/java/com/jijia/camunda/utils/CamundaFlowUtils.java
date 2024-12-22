package com.jijia.camunda.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jijia.camunda.constants.AssigneeTypeEnums;
import com.jijia.camunda.constants.CamundaWorkConstants;
import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.dto.ChildNode;
import com.jijia.camunda.domain.dto.Properties;
import com.jijia.camunda.mapper.newM.CmdModelMapper;
import com.jijia.common.core.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Slf4j
public class CamundaFlowUtils {

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private CmdModelMapper cmdModelMapper;
    @Resource
    private AssigneeTypeUtils assigneeTypeUtils;

    public List<String> calculateTaskCandidateUsers(DelegateExecution execution) {
        // 由于会调用两次
        // 用于第二次直接返回
        if (StringUtils.isBlank(execution.getCurrentActivityId())) {
            Map<String, Object> variables = execution.getVariables();
            Set<String> strings = variables.keySet();
            String variableName = "";
            for (String string : strings) {
                if (string.endsWith("AssigneeList")) {
                    variableName = string;
                }
            }
            return MapUtil.get(variables, variableName, List.class);
        }
        Set<Long> assigneeList = new HashSet<>();

        // 找到模型实例
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().
                processDefinitionId(execution.getProcessDefinitionId()).singleResult();

        // 找到模型配置
        LambdaQueryWrapper<CmdModel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CmdModel::getVersion, processDefinition.getVersion());
        lambdaQueryWrapper.eq(CmdModel::getCode, processDefinition.getKey());
        CmdModel cmdModel = cmdModelMapper.selectOne(lambdaQueryWrapper);
        if (cmdModel == null) {
            throw new GlobalException("查找审批人失败,请联系管理员重试");
        }

        // 是否会签
        String currentActivityId = execution.getCurrentActivityId();
        if (StringUtils.endsWith(execution.getCurrentActivityId(), CamundaWorkConstants.MULTI_BODY)) {
            currentActivityId = currentActivityId.replace(CamundaWorkConstants.MULTI_BODY, "");
        }

        // 找到对应节点
        ChildNode childNode = JSONObject.parseObject(cmdModel.getNodeJsonData(), new TypeReference<ChildNode>() {
        });
        ChildNode currentNode = BpmnUtils.getChildNode(childNode, currentActivityId);

        if (currentNode == null) {
            throw new GlobalException("查找审批人失败,请联系管理员重试");
        }

        // 获取配置项
        Properties props = currentNode.getProps();
        Map<String, Object> nobody = props.getNobody();
        String variable = currentActivityId + "AssigneeList";
        String assignedType = props.getAssignedType();
        AssigneeTypeEnums typeEnums = null;
        try {
            typeEnums = AssigneeTypeEnums.valueOf(assignedType);
        } catch (IllegalArgumentException e) {
            throw new GlobalException("找不到审批人类型,请检查配置!!!");
        }

        switch (typeEnums) {
            case POST:
                assigneeTypeUtils.getAssignUserByPost(assigneeList, props);
                break;
            case ROLE:
                assigneeTypeUtils.getAssignUserByRole(assigneeList, props);
                break;
            case ASSIGN_USER:
                assigneeTypeUtils.getAssignUser(assigneeList, props);
                break;
            case SELF:
                assigneeTypeUtils.getAssignUserBySelf(assigneeList, execution);
                break;
            case SELF_SELECT:
                assigneeTypeUtils.getAssignUserBySelfSelect(assigneeList, execution, currentActivityId);
                break;
        }

        if (CollUtil.isEmpty(assigneeList)) {
            String handler = MapUtil.getStr(nobody, "handler");
            AssigneeTypeEnums notTypeEnums = null;
            try {
                notTypeEnums = AssigneeTypeEnums.valueOf(handler);
            } catch (IllegalArgumentException e) {
                throw new GlobalException("找不到审批人类型,请检查配置!!!");
            }

            switch (notTypeEnums) {
                case TO_USER:
                    assigneeTypeUtils.getNotAssignUserByUser(assigneeList, execution, nobody, variable);
                    break;
                case TO_REFUSE:
                    assigneeTypeUtils.getNotAssignUserByRefuse(assigneeList, execution, variable);
                    break;
                case TO_ADMIN:
                    assigneeTypeUtils.getNotAssignUserByAdmin(assigneeList, execution, variable);
                    break;
                case TO_PASS:
                    assigneeTypeUtils.getNotAssignUserByPass(assigneeList, execution, variable);
                    break;
                default:
                    throw new GlobalException("找不到为空时的类型,请检查配置!!!");
            }

        } else {
            execution.setVariable(variable, assigneeList);
        }


        execution.setVariableLocal(currentActivityId + "AssigneeList", assigneeList);

        return assigneeList.stream().map(String::valueOf).collect(Collectors.toList());
    }


}
