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
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
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
@TaskAnnotation(TaskCompleteType = {TaskApplyType.AGREE, TaskApplyType.RESOLVE})
public class AgreeTaskImpl extends AbstractTaskApplyStrategy {

    @Resource
    private TaskService taskService;
    @Resource
    private IdentityService identityService;

    @Override
    public void CompleteTask(HandleDataDTO handleDataDTO) {

        // 获取任务
        Task task = taskService.createTaskQuery().taskId(handleDataDTO.getTaskId()).singleResult();
        setFormDate(task.getProcessInstanceId(), handleDataDTO);

        // 设置审批人
        identityService.setAuthenticatedUserId(SecurityUtils.getUserId().toString());

        // 设置审批意见
        addComments(task, handleDataDTO);

        if (TaskApplyType.AGREE.getType().equals(handleDataDTO.getHandleType())) {
            // 完成任务
            taskService.complete(task.getId());
        } else {
            taskService.resolveTask(task.getId());
        }
    }
}
