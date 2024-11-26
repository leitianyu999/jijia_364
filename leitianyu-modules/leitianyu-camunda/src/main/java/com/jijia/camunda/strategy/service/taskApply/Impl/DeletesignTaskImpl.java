package com.jijia.camunda.strategy.service.taskApply.Impl;

import com.jijia.camunda.annotation.TaskAnnotation;
import com.jijia.camunda.domain.dto.HandleDataDTO;
import com.jijia.camunda.domain.enums.TaskApplyType;
import com.jijia.camunda.strategy.service.taskApply.abstractImpl.AbstractTaskApplyStrategy;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/11/25
 */
@Component
@TaskAnnotation(TaskCompleteType = TaskApplyType.DELETESIGN)
public class DeletesignTaskImpl extends AbstractTaskApplyStrategy {


    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;

    @Override
    public void CompleteTask(HandleDataDTO handleDataDTO) {

        List<String> executionIds = handleDataDTO.getExecutionIds();
        for (String executionId : executionIds) {
            Task task = taskService.createTaskQuery().executionId(executionId).singleResult();
            String activityId = task.getTaskDefinitionKey()+":"+executionId;
            runtimeService.createProcessInstanceModification(task.getProcessInstanceId())
                    .cancelActivityInstance(activityId)
                    .execute();
        }
    }
}
