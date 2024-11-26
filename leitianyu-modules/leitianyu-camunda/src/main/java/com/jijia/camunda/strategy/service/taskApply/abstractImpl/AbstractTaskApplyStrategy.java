package com.jijia.camunda.strategy.service.taskApply.abstractImpl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jijia.camunda.constants.CamundaWorkConstants;
import com.jijia.camunda.constants.CommonConstants;
import com.jijia.camunda.domain.dto.HandleDataDTO;
import com.jijia.camunda.strategy.service.taskApply.TaskApplyStrategy;
import com.jijia.common.security.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leitianyu
 */
@Component
public abstract class AbstractTaskApplyStrategy implements TaskApplyStrategy {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private IdentityService identityService;

    protected Task complete(HandleDataDTO handleDataDTO) {
        identityService.setAuthenticatedUserId(SecurityUtils.getUserId().toString());

        String taskId = handleDataDTO.getTaskId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        Map<String,Object> map=new HashMap<>();
        map.put(CommonConstants.PROCESS_STATUS, CommonConstants.BUSINESS_STATUS_1);
        setFormDate(map, task.getProcessInstanceId(), handleDataDTO);

        addComments(task,handleDataDTO);

        return task;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected Map<String, Object> setFormDate(Map<String, Object> OtherMap, String processInstanceId, HandleDataDTO handleDataDTO) {
        // 设置表单数据
        JSONObject formData = handleDataDTO.getFormData();
        HashMap<String, Object> map = new HashMap<>();
        if (formData != null && !formData.isEmpty()) {
            Map formValue = JSONObject.parseObject(formData.toJSONString(), new TypeReference<Map>() {
            });
            map.putAll(formValue);
            map.put(CommonConstants.FORM_VAR, formData);
        }

        map.putAll(OtherMap);
        // 设置流程变量
        runtimeService.setVariables(processInstanceId, map);

        return map;
    }


    protected Map<String, Object> setFormDate(String processInstanceId, HandleDataDTO handleDataDTO) {
        return setFormDate(new HashMap<>(), processInstanceId, handleDataDTO);
    }

    @SuppressWarnings("deprecation")
    protected void addComments(HistoricTaskInstance task, HandleDataDTO handleDataDTO) {
        // 添加审批意见
        if (StringUtils.isNotBlank(handleDataDTO.getComments())) {
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "opinion" + CamundaWorkConstants.COMMENT_SPLIT + handleDataDTO.getComments());
        }

        // 添加签名信息
        if (StringUtils.isNotBlank(handleDataDTO.getSignInfo())) {
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "sign" + CamundaWorkConstants.COMMENT_SPLIT + handleDataDTO.getSignInfo());
        }
    }

    @SuppressWarnings("deprecation")
    protected void addComments(Task task, HandleDataDTO handleDataDTO) {
        // 添加审批意见
        if (StringUtils.isNotBlank(handleDataDTO.getComments())) {
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "opinion" + CamundaWorkConstants.COMMENT_SPLIT + handleDataDTO.getComments());
        }

        // 添加签名信息
        if (StringUtils.isNotBlank(handleDataDTO.getSignInfo())) {
            taskService.addComment(task.getId(), task.getProcessInstanceId(), "sign" + CamundaWorkConstants.COMMENT_SPLIT + handleDataDTO.getSignInfo());
        }
    }


}
