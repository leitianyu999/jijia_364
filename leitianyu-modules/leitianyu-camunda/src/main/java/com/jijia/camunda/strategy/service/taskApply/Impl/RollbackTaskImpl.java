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
import java.util.ArrayList;
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
@TaskAnnotation(TaskCompleteType = TaskApplyType.ROLLBACK)
public class RollbackTaskImpl extends AbstractTaskApplyStrategy {


    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private IdentityService identityService;

    @Override
    public void CompleteTask(HandleDataDTO handleDataDTO) {
//
//        identityService.setAuthenticatedUserId(SecurityUtils.getUserId().toString());
//
//        String taskId = handleDataDTO.getTaskId();
//        String processInstanceId = handleDataDTO.getProcessInstanceId();
//
//        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
//        Task task = null;
////        List<String> taskIds = new ArrayList<>();
//
//        for (Task task1 : list) {
//            if(task1.getId().equals(taskId)){
//                task=task1;
//                break;
//            }
////            taskIds.add(task1.getTaskDefinitionKey());
//        }
//
//        Map<String,Object> map=new HashMap<>();
//        map.put(CommonConstants.PROCESS_STATUS, CommonConstants.BUSINESS_STATUS_3);
//        setFormDate(map, task.getProcessInstanceId(), handleDataDTO);
//
//        addComments(task, handleDataDTO);
    }
}
