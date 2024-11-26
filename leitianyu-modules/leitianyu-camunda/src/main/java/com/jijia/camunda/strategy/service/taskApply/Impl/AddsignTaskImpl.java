package com.jijia.camunda.strategy.service.taskApply.Impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jijia.camunda.annotation.ModelAnnotation;
import com.jijia.camunda.annotation.TaskAnnotation;
import com.jijia.camunda.constants.CommonConstants;
import com.jijia.camunda.domain.dto.HandleDataDTO;
import com.jijia.camunda.domain.enums.ModelType;
import com.jijia.camunda.domain.enums.TaskApplyType;
import com.jijia.camunda.strategy.service.taskApply.abstractImpl.AbstractTaskApplyStrategy;
import com.jijia.common.security.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/11/25
 */
@Component
@TaskAnnotation(TaskCompleteType = TaskApplyType.ADDSIGN)
public class AddsignTaskImpl extends AbstractTaskApplyStrategy {


    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private IdentityService identityService;

    @Override
    public void CompleteTask(HandleDataDTO handleDataDTO) {

        Task task = complete(handleDataDTO);


        Map<String,Object> variableMap= new HashMap<>();

        // ？？？？？？？？？？？？？
        variableMap.put("assigneeName",handleDataDTO.getMultiAddUserId());
        runtimeService.createProcessInstanceModification(task.getProcessInstanceId())
                .startBeforeActivity(task.getTaskDefinitionKey())
                .execute();
    }
}
