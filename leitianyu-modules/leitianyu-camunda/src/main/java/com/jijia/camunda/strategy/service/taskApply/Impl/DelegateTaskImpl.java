package com.jijia.camunda.strategy.service.taskApply.Impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jijia.camunda.annotation.TaskAnnotation;
import com.jijia.camunda.constants.CamundaWorkConstants;
import com.jijia.camunda.constants.CommonConstants;
import com.jijia.camunda.domain.dto.HandleDataDTO;
import com.jijia.camunda.domain.enums.TaskApplyType;
import com.jijia.camunda.strategy.service.taskApply.abstractImpl.AbstractTaskApplyStrategy;
import com.jijia.common.security.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/11/25
 */
@Component
@TaskAnnotation(TaskCompleteType = TaskApplyType.DELEGATE)
public class DelegateTaskImpl extends AbstractTaskApplyStrategy {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private IdentityService identityService;

    @Override
    public void CompleteTask(HandleDataDTO handleDataDTO) {

        // 获取任务
        String taskId = handleDataDTO.getTaskId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        // 设置表单数据
        Map<String, Object> map = setFormDate(task.getProcessInstanceId(), handleDataDTO);

        // 设置流程变量
        runtimeService.setVariables(task.getProcessInstanceId(),map);

        // 设置审批人
        identityService.setAuthenticatedUserId(SecurityUtils.getUserId().toString());

        // 添加审批意见
        addComments(task,handleDataDTO);

        // 委托任务
        Long delegateUserId = handleDataDTO.getDelegateUserId();
        taskService.delegateTask(task.getId(),delegateUserId.toString());
    }
}
