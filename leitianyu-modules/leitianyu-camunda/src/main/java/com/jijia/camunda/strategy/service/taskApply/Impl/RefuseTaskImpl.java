package com.jijia.camunda.strategy.service.taskApply.Impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jijia.camunda.annotation.TaskAnnotation;
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
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/11/25
 */
@Component
@TaskAnnotation(TaskCompleteType = TaskApplyType.REFUSE)
public class RefuseTaskImpl extends AbstractTaskApplyStrategy {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private IdentityService identityService;

    @Override
    public void CompleteTask(HandleDataDTO handleDataDTO) {

        // 当前用户
        identityService.setAuthenticatedUserId(SecurityUtils.getUserId().toString());

        String taskId = handleDataDTO.getTaskId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();


        HashMap<String, Object> map = new HashMap<>();
        // 设置流程状态
        map.put(CommonConstants.PROCESS_STATUS, CommonConstants.BUSINESS_STATUS_3);
        setFormDate(map, task.getProcessInstanceId(), handleDataDTO);

        // 添加审批意见
        addComments(task, handleDataDTO);
        runtimeService.deleteProcessInstance(task.getProcessInstanceId(),"拒绝");
    }
}
