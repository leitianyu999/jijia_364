package com.jijia.camunda.strategy.service.taskApply.Impl;

import com.jijia.camunda.annotation.TaskAnnotation;
import com.jijia.camunda.constants.CommonConstants;
import com.jijia.camunda.domain.dto.HandleDataDTO;
import com.jijia.camunda.domain.enums.TaskApplyType;
import com.jijia.camunda.strategy.service.taskApply.abstractImpl.AbstractTaskApplyStrategy;
import com.jijia.common.security.utils.SecurityUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
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
@TaskAnnotation(TaskCompleteType = TaskApplyType.REVOKE)
public class RevokeTaskImpl extends AbstractTaskApplyStrategy {


    @Resource
    private RuntimeService runtimeService;
    @Resource
    private HistoryService historyService;
    @Resource
    private IdentityService identityService;


    @Override
    public void CompleteTask(HandleDataDTO handleDataDTO) {

        // 设置当前用户
        identityService.setAuthenticatedUserId(SecurityUtils.getUserId().toString());

        String taskId = handleDataDTO.getTaskId();
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();


        Map<String, Object> map = new HashMap<>();
        map.put(CommonConstants.PROCESS_STATUS, CommonConstants.BUSINESS_STATUS_2);
        runtimeService.setVariables(task.getProcessInstanceId(), map);


        addComments(task, handleDataDTO);
        runtimeService.deleteProcessInstance(task.getProcessInstanceId(), "撤销");
    }
}
