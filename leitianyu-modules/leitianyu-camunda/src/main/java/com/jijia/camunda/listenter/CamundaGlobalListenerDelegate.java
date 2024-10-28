package com.jijia.camunda.listenter;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.model.bpmn.instance.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 本项目为Apache2.0协议 请保留此协议头 ,否则即为违反Apache2.0协议,可以视为侵权
 * @author Dr4JavaEE
 */
@Slf4j
public class CamundaGlobalListenerDelegate implements ExecutionListener, TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
//        BpmTaskService bpmTaskService = SpringUtil.getBean(BpmTaskService.class);
//        Map<String, Object> variables = delegateTask.getVariables();
//        String processInstanceName = MapUtil.getStr(variables, WorkFlowConstants.PROCESS_INSTANCE_NAME);
//        Long startUserId = MapUtil.getLong(variables, WorkFlowConstants.PROCESS_INSTANCE_STARTER_USER_ID);
//        if(TaskListener.EVENTNAME_CREATE.equals(delegateTask.getEventName())){
//            CamundaTaskDTO camundaTaskDTO = new CamundaTaskDTO();
//            camundaTaskDTO.setTaskId(delegateTask.getId());
//            camundaTaskDTO.setProcessInstanceId(delegateTask.getProcessInstanceId());
//            camundaTaskDTO.setAssigneeUserId(Long.valueOf(delegateTask.getAssignee()));
//            camundaTaskDTO.setCreateTime(delegateTask.getCreateTime());
//            camundaTaskDTO.setProcessDefinitionName(processInstanceName);
//            camundaTaskDTO.setProcessDefinitionId(delegateTask.getProcessDefinitionId());
//            camundaTaskDTO.setProcessStartUserId(startUserId);
//            camundaTaskDTO.setName(delegateTask.getName());
//            bpmTaskService.createTaskExt(camundaTaskDTO);
//        }
//        else if(TaskListener.EVENTNAME_ASSIGNMENT.equals(delegateTask.getEventName())){
//            CamundaTaskDTO camundaTaskDTO = new CamundaTaskDTO();
//            camundaTaskDTO.setTaskId(delegateTask.getId());
//            camundaTaskDTO.setProcessInstanceId(delegateTask.getProcessInstanceId());
//            camundaTaskDTO.setAssigneeUserId(Long.valueOf(delegateTask.getAssignee()));
//            camundaTaskDTO.setCreateTime(delegateTask.getCreateTime());
//            camundaTaskDTO.setName(delegateTask.getName());
//            camundaTaskDTO.setProcessStartUserId(startUserId);
//            camundaTaskDTO.setProcessDefinitionName(processInstanceName);
//            camundaTaskDTO.setProcessDefinitionId(delegateTask.getProcessDefinitionId());
//            bpmTaskService.updateTaskExtAssign(camundaTaskDTO);
//        }
//        else if(TaskListener.EVENTNAME_COMPLETE.equals(delegateTask.getEventName())){
//            CamundaTaskDTO camundaTaskDTO = new CamundaTaskDTO();
//            camundaTaskDTO.setTaskId(delegateTask.getId());
//            camundaTaskDTO.setProcessInstanceId(delegateTask.getProcessInstanceId());
//            camundaTaskDTO.setAssigneeUserId(Long.valueOf(delegateTask.getAssignee()));
//            camundaTaskDTO.setCreateTime(delegateTask.getCreateTime());
//            camundaTaskDTO.setName(delegateTask.getName());
//            camundaTaskDTO.setProcessStartUserId(startUserId);
//            camundaTaskDTO.setProcessDefinitionName(processInstanceName);
//            camundaTaskDTO.setProcessDefinitionId(delegateTask.getProcessDefinitionId());
//            bpmTaskService.updateTaskExtComplete(camundaTaskDTO);
//        }
    }


    @Override
    public void notify(DelegateExecution execution) throws Exception {
//        BpmProcessInstanceService bpmProcessInstanceService = SpringUtil.getBean(BpmProcessInstanceService.class);
//        BpmTaskAssignRuleService bpmTaskAssignRuleService = SpringUtil.getBean(BpmTaskAssignRuleService.class);
//        FlowElement bpmnModelElementInstance = execution.getBpmnModelElementInstance();
//        Map<String, Object> variables = execution.getVariables();
//        String processInstanceName = MapUtil.getStr(variables, WorkFlowConstants.PROCESS_INSTANCE_NAME);
//        Long startUserId = MapUtil.getLong(variables, WorkFlowConstants.PROCESS_INSTANCE_STARTER_USER_ID);
//        if(bpmnModelElementInstance instanceof UserTask){
//            UserTask userTask =(UserTask)bpmnModelElementInstance;
//            LoopCharacteristics loopCharacteristics = userTask.getLoopCharacteristics();
//            if(loopCharacteristics==null){
//                Set<Long> users = bpmTaskAssignRuleService.calculateTaskCandidateUsers(execution);
//                List<Long> userList= new ArrayList<>(users);
//                execution.setVariable("assignee",userList.get(0)+"");
//            }
//        }
//        else if(bpmnModelElementInstance instanceof StartEvent){
//            CamundaProcessInstanceDTO camundaProcessInstanceDTO = new CamundaProcessInstanceDTO();
//            camundaProcessInstanceDTO.setProcessInstanceId(execution.getProcessInstanceId());
//            camundaProcessInstanceDTO.setProcessDefinitionName(processInstanceName);
//            camundaProcessInstanceDTO.setProcessStartUserId(startUserId);
//            camundaProcessInstanceDTO.setProcessDefinitionId(execution.getProcessDefinitionId());
//            bpmProcessInstanceService.createProcessInstanceExt(camundaProcessInstanceDTO);
//        }
//        else if(bpmnModelElementInstance instanceof EndEvent) {
//            CamundaProcessInstanceDTO camundaProcessInstanceDTO = new CamundaProcessInstanceDTO();
//            camundaProcessInstanceDTO.setProcessInstanceId(execution.getProcessInstanceId());
//            camundaProcessInstanceDTO.setProcessDefinitionName(processInstanceName);
//            camundaProcessInstanceDTO.setProcessStartUserId(startUserId);
//            camundaProcessInstanceDTO.setProcessDefinitionId(execution.getProcessDefinitionId());
//            bpmProcessInstanceService.updateProcessInstanceExtComplete(camundaProcessInstanceDTO);
//        }


    }

}