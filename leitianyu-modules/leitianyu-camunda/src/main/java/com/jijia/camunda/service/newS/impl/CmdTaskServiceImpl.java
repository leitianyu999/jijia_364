package com.jijia.camunda.service.newS.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jijia.camunda.constants.CamundaWorkConstants;
import com.jijia.camunda.constants.CommonConstants;
import com.jijia.camunda.domain.CmdForm;
import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.CmdModelForm;
import com.jijia.camunda.domain.dto.HandleDataDTO;
import com.jijia.camunda.domain.dto.ProcessInstanceDto;
import com.jijia.camunda.domain.dto.vo.UserInfo;
import com.jijia.camunda.domain.enums.TaskApplyType;
import com.jijia.camunda.domain.vo.HandleDataVO;
import com.jijia.camunda.domain.vo.HistoryProcessInstanceVO;
import com.jijia.camunda.domain.vo.TaskVO;
import com.jijia.camunda.mapper.newM.CmdFormMapper;
import com.jijia.camunda.mapper.newM.CmdModelFormMapper;
import com.jijia.camunda.mapper.newM.CmdModelMapper;
import com.jijia.camunda.service.newS.CmdFormService;
import com.jijia.camunda.service.newS.CmdModelService;
import com.jijia.camunda.service.newS.CmdTaskService;
import com.jijia.camunda.strategy.handler.HandlerTaskContext;
import com.jijia.common.core.exception.GlobalException;
import com.jijia.common.core.web.page.PageDomain;
import com.jijia.common.core.web.page.TableDataInfo;
import com.jijia.common.core.web.page.TableSupport;
import com.jijia.common.security.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Attachment;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.DelegationState;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/11/25
 */
@Service
public class CmdTaskServiceImpl implements CmdTaskService {


    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private HistoryService historyService;
    @Resource
    private TaskService taskService;
    @Resource
    private IdentityService identityService;
    @Resource
    private CmdModelMapper cmdModelMapper;
    @Resource
    private CmdFormMapper cmdFormMapper;
    @Resource
    private CmdModelFormMapper cmdModelFormMapper;


    @Override
    public Long start(ProcessInstanceDto processInstanceDto) {
        try{

            JSONObject formData = processInstanceDto.getFormData();
            Long userId = SecurityUtils.getUserId();

            // 设置流程发起人
            identityService.setAuthenticatedUserId(userId.toString());

            // 设置流程变量
            Map<String,Object> processVariables= new HashMap<>();
            processVariables.put(CommonConstants.FORM_VAR,formData);
            processVariables.put(CommonConstants.PROCESS_STATUS,CommonConstants.BUSINESS_STATUS_1);
            processVariables.put(CommonConstants.APPLY_USER_ID,userId);
            processVariables.put("root",userId);

            // 遍历审批
            Map<String, List<UserInfo>> processUsers = processInstanceDto.getProcessUsers();
            if(CollUtil.isNotEmpty(processUsers)){
                Set<String> strings = processUsers.keySet();
                for (String string : strings) {
                    List<UserInfo> selectUserInfo = processUsers.get(string);
                    List<String> users=new ArrayList<>();
                    for (UserInfo userInfo : selectUserInfo) {
                        users.add(userInfo.getId());
                    }
                    processVariables.put(string,users);
                }
            }

            Map formValue = JSONObject.parseObject(formData.toJSONString(), new TypeReference<Map>() {
            });
            processVariables.putAll(formValue);
            // 启动流程
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(processInstanceDto.getProcessDefinitionId(), processVariables);
            //手动完成第一个任务
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
            if(task!=null){
                taskService.complete(task.getId());
            }
            return Long.valueOf(processInstance.getId());
        }
        catch (Exception e){
            throw  new GlobalException("流程启动失败" + e.getMessage());
        }
    }

    @Override
    public TableDataInfo applyList() {

        // 获取分页信息
        PageDomain pageDomain = TableSupport.buildPageRequest();

        // 分页查询历史表
        List<HistoricProcessInstance> historicProcessInstances =
                historyService.createHistoricProcessInstanceQuery()
                        .startedBy(SecurityUtils.getUserId().toString())
                        .orderByProcessInstanceStartTime().desc()
                        .listPage((pageDomain.getPageNum() - 1) * pageDomain.getPageSize(), pageDomain.getPageSize());
        // 总数
        long count = historyService.createHistoricProcessInstanceQuery()
                .startedBy(SecurityUtils.getUserId().toString()).count();


        List<HistoryProcessInstanceVO> historyProcessInstanceVOS= new ArrayList<>();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            Map<String, Object> processVariables = getVariables(historicProcessInstance.getId());
            HistoryProcessInstanceVO historyProcessInstanceVO=new HistoryProcessInstanceVO();
            historyProcessInstanceVO.setProcessInstanceId(historicProcessInstance.getId());
            historyProcessInstanceVO.setProcessDefinitionName(historicProcessInstance.getProcessDefinitionName());
            historyProcessInstanceVO.setStartTime(historicProcessInstance.getStartTime());
            historyProcessInstanceVO.setEndTime(historicProcessInstance.getEndTime());

            Boolean flag= historicProcessInstance.getEndTime() != null;
            historyProcessInstanceVO.setCurrentActivityName(getCurrentName(historicProcessInstance.getId(),flag,historicProcessInstance.getProcessDefinitionId()));
            historyProcessInstanceVO.setBusinessStatus(MapUtil.getStr(processVariables,CommonConstants.PROCESS_STATUS));


            long totalTimes = historicProcessInstance.getEndTime()==null?
                    (Calendar.getInstance().getTimeInMillis()-historicProcessInstance.getStartTime().getTime()):
                    (historicProcessInstance.getEndTime().getTime()-historicProcessInstance.getStartTime().getTime());
            long dayCount = totalTimes /(1000*60*60*24);//计算天
            long restTimes = totalTimes %(1000*60*60*24);//剩下的时间用于计于小时
            long hourCount = restTimes/(1000*60*60);//小时
            restTimes = restTimes % (1000*60*60);
            long minuteCount = restTimes / (1000*60);

            String spendTimes = dayCount+"天"+hourCount+"小时"+minuteCount+"分";
            historyProcessInstanceVO.setDuration(spendTimes);
            historyProcessInstanceVOS.add(historyProcessInstanceVO);
        }

        TableDataInfo page = new TableDataInfo();
        page.setRows(historyProcessInstanceVOS);
        page.setTotal(count);
        return page;
    }

    @Override
    public TableDataInfo toDoList() {
        // 获取分页信息
        PageDomain pageDomain = TableSupport.buildPageRequest();

        // 查询待办任务
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(SecurityUtils.getUserId().toString())
                .orderByTaskCreateTime().desc()
                .listPage((pageDomain.getPageNum() - 1) * pageDomain.getPageSize(), pageDomain.getPageSize());
        long count = taskService.createTaskQuery().taskAssignee(SecurityUtils.getUserId().toString()).count();

        List<TaskVO> taskVOS= new ArrayList<>();
        TableDataInfo page = new TableDataInfo();


        List<String> taskIds= new ArrayList<>();
        for (Task task : tasks) {
            Map<String, Object> processVariables = getVariables(task.getProcessInstanceId());
            String id = MapUtil.getStr(processVariables, CommonConstants.APPLY_USER_ID);
            taskIds.add(id);
        }

        for (Task task : tasks) {
            HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            String name = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).singleResult().getName();
            Map<String, Object> processVariables = getVariables(task.getProcessInstanceId());
            TaskVO taskVO=new TaskVO();
            taskVO.setTaskId(task.getId());
            taskVO.setProcessInstanceId(task.getProcessInstanceId());
            taskVO.setProcessDefinitionName(name);
            taskVO.setStartUserId(Long.valueOf(MapUtil.getStr(processVariables, CommonConstants.APPLY_USER_ID)));
            taskVO.setStartTime(processInstance.getStartTime());
            taskVO.setCurrentActivityName(getCurrentName(processInstance.getId(),false,processInstance.getProcessDefinitionId()));

            taskVO.setBusinessStatus(MapUtil.getStr(processVariables,CommonConstants.PROCESS_STATUS));
            taskVO.setTaskCreatedTime(task.getCreateTime());
            DelegationState delegationState = task.getDelegationState();
            if(delegationState!=null){
                taskVO.setDelegationState(delegationState);
            }
            taskVOS.add(taskVO);

        }

        page.setRows(taskVOS);
        page.setTotal(count);
        return page;
    }

    @Override
    public TableDataInfo doneList() {

        // 获取分页信息
        PageDomain pageDomain = TableSupport.buildPageRequest();

        List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(SecurityUtils.getUserId().toString())
                .finished()
                .orderByHistoricActivityInstanceStartTime().desc()
                .listPage((pageDomain.getPageNum() - 1) * pageDomain.getPageSize(), pageDomain.getPageSize());
        long count = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(SecurityUtils.getUserId().toString()).finished().count();
        List<TaskVO> taskVOS= new ArrayList<>();
        TableDataInfo page = new TableDataInfo();


        for (HistoricTaskInstance task : tasks) {
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            Boolean flag=historicProcessInstance.getEndTime()==null?false:true;
            String name = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult().getName();
            Map<String, Object> processVariables = getVariables(task.getProcessInstanceId());
            TaskVO taskVO=new TaskVO();
            taskVO.setTaskId(task.getId());
            taskVO.setTaskName(task.getName());
            taskVO.setProcessInstanceId(task.getProcessInstanceId());
            taskVO.setProcessDefinitionName(name);
            taskVO.setStartUserId(Long.valueOf(MapUtil.getStr(processVariables, CommonConstants.START_USER_INFO)));
            taskVO.setStartTime(historicProcessInstance.getStartTime());
            taskVO.setCurrentActivityName(getCurrentName(task.getProcessInstanceId(),flag,task.getProcessDefinitionId()));
            taskVO.setBusinessStatus(MapUtil.getStr(processVariables,CommonConstants.PROCESS_STATUS));
            taskVO.setEndTime(task.getEndTime());

            long totalTimes = task.getEndTime()==null?
                    (Calendar.getInstance().getTimeInMillis()-task.getStartTime().getTime()):
                    (task.getEndTime().getTime()-task.getStartTime().getTime());
            long dayCount = totalTimes /(1000*60*60*24);//计算天
            long restTimes = totalTimes %(1000*60*60*24);//剩下的时间用于计于小时
            long hourCount = restTimes/(1000*60*60);//小时
            restTimes = restTimes % (1000*60*60);
            long minuteCount = restTimes / (1000*60);
            String spendTimes = dayCount+"天"+hourCount+"小时"+minuteCount+"分";
            taskVO.setDuration(spendTimes);
            taskVOS.add(taskVO);
        }

        page.setRows(taskVOS);
        page.setTotal(count);
        return page;
    }

    @Override
    public void complete(HandleDataDTO handleDataDTO) {
        HandlerTaskContext.getInstance(TaskApplyType.getByType(handleDataDTO.getHandleType())).CompleteTask(handleDataDTO);
    }

    @Override
    public Map<String, String> getRollbackNodes(HandleDataDTO handleDataDTO) {
        List<HistoricActivityInstance> list = historyService.
                createHistoricActivityInstanceQuery().
                finished().processInstanceId(handleDataDTO.getProcessInstanceId()).list();
        Map<String,String> nodes=new HashMap<>();
        for (HistoricActivityInstance activityInstance : list) {
            nodes.put(activityInstance.getActivityId(),activityInstance.getActivityName());
        }
        return nodes;
    }

    @Override
    public void instanceInfo(HandleDataDTO handleDataDTO) {

        String processInstanceId = handleDataDTO.getProcessInstanceId();
        // 获取流程实例
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
                .singleResult();

        // 获取流程定义key
        String processDefinitionKey = historicProcessInstance.getProcessDefinitionKey();
        Integer processDefinitionVersion = historicProcessInstance.getProcessDefinitionVersion();

        LambdaQueryWrapper<CmdModel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CmdModel::getVersion, processDefinitionVersion).eq(CmdModel::getKey, processDefinitionKey);
        CmdModel model = cmdModelMapper.selectOne(queryWrapper);
        if (model == null) {
            throw new GlobalException("无此模型");
        }
        CmdModelForm cmdModelForm = cmdModelFormMapper.selectById(model.getModelId());
        CmdForm cmdForm;
        if (cmdModelForm != null) {
            cmdForm = cmdFormMapper.selectById(cmdModelForm.getFormId());
        }



//        ProcessTemplates processTemplates = processTemplateService.getById(processDefinitionKey.replace(PROCESS_PREFIX,""));
//        processTemplates.setLogo(processTemplates.getIcon());
//        processTemplates.setFormId(processTemplates.getTemplateId());
//        processTemplates.setFormName(processTemplates.getTemplateName());
//        processTemplates.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());

        HandleDataVO handleDataVO =new HandleDataVO();
        Map<String, Object> processVariables = getVariables(processInstanceId);

        handleDataVO.setProcessInstanceId(historicProcessInstance.getId());
        JSONObject jsonObject = (JSONObject) processVariables.get(FORM_VAR);
        handleDataVO.setFormData(jsonObject);
        String process = processTemplates.getProcess();
        ChildNode childNode = JSONObject.parseObject(process, new TypeReference<ChildNode>(){});
        SettingsInfo settingsInfo = JSONObject.parseObject(processTemplates.getSettings(), new TypeReference<SettingsInfo>() {});
        Boolean sign = settingsInfo.getSign();
        ChildNode currentNode=null;
        if(StringUtils.isNotBlank(HandleDataDTO.getTaskId())){
            HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(HandleDataDTO.getTaskId()).singleResult();
            currentNode = getChildNode(childNode, historicTaskInstance.getTaskDefinitionKey());
            List<FormOperates> formPerms = currentNode.getProps().getFormPerms();
            if(CollUtil.isNotEmpty(formPerms)){
                Iterator<FormOperates> iterator = formPerms.iterator();
                while (iterator.hasNext()){
                    FormOperates next = iterator.next();
                    if("H".equals(next.getPerm())){
//                        iterator.remove();
                        if(jsonObject!=null){
                            jsonObject.remove(next.getId());
                        }
                    }
                }
            }
            handleDataVO.setCurrentNode(currentNode);
            handleDataVO.setTaskId(HandleDataDTO.getTaskId());
        }

        if(sign){
            handleDataVO.setSignFlag(true);
        }

        else{
            if(StringUtils.isNotBlank(HandleDataDTO.getTaskId())){
                if(currentNode!=null){
                    Boolean signFlag = currentNode.getProps().getSign();
                    if(signFlag!=null && signFlag){
                        handleDataVO.setSignFlag(true);
                    }
                    else{
                        handleDataVO.setSignFlag(false);
                    }
                }
            }
            else {
                handleDataVO.setSignFlag(false);
            }
        }




        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(historicProcessInstance.getId()).list();
        Map<String,List<HistoricActivityInstance>> historicActivityInstanceMap =new HashMap<>();
        for (HistoricActivityInstance historicActivityInstance : list) {
            List<HistoricActivityInstance> historicActivityInstances = historicActivityInstanceMap.get(historicActivityInstance.getActivityId());
            if(historicActivityInstances==null){
                historicActivityInstances =new ArrayList<>();
                historicActivityInstances.add(historicActivityInstance);
                historicActivityInstanceMap.put(historicActivityInstance.getActivityId(),historicActivityInstances);
            }
            else{
                historicActivityInstances.add(historicActivityInstance);
                historicActivityInstanceMap.put(historicActivityInstance.getActivityId(),historicActivityInstances);
            }
        }
        Collection<FlowNode> flowElements = repositoryService.getBpmnModelInstance(historicProcessInstance.getProcessDefinitionId()).getModelElementsByType(FlowNode.class);
        List<String> runningList= new ArrayList<>();
        handleDataVO.setRunningList(runningList);
        List<String> endList=new ArrayList<>();
        handleDataVO.setEndList(endList);
        List<String> noTakeList=new ArrayList<>();
        handleDataVO.setNoTakeList(noTakeList);
        Map<String,List<TaskDetailVO>> deatailMap =new HashMap<>();
        List<Comment> processInstanceComments = taskService.getProcessInstanceComments(historicProcessInstance.getId());
        List<Attachment> processInstanceAttachments = taskService.getProcessInstanceAttachments(historicProcessInstance.getId());
        for (FlowElement flowElement : flowElements) {
            List<TaskDetailVO> detailVOList =new ArrayList<>();
            List<HistoricActivityInstance> historicActivityInstanceList = historicActivityInstanceMap.get(flowElement.getId());
            if(CollUtil.isNotEmpty(historicActivityInstanceList)){
                for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
                    if(historicActivityInstance.getEndTime()!=null){
                        if("startEvent".equalsIgnoreCase(historicActivityInstance.getActivityType()) ||"endEvent".equalsIgnoreCase(historicActivityInstance.getActivityType())){
                            TaskDetailVO taskDetailVO = new TaskDetailVO();
                            taskDetailVO.setActivityId(historicActivityInstance.getActivityId());
                            taskDetailVO.setName(historicActivityInstance.getActivityName());
                            taskDetailVO.setCreateTime(historicActivityInstance.getStartTime());
                            taskDetailVO.setEndTime(historicActivityInstance.getEndTime());
                            detailVOList.add(taskDetailVO);
                            deatailMap.put(historicActivityInstance.getActivityId(),detailVOList);
                            endList.add(historicActivityInstance.getActivityId());
                        }
                        else if ("userTask".equalsIgnoreCase(historicActivityInstance.getActivityType())){
                            List<TaskDetailVO> voList = deatailMap.get(historicActivityInstance.getActivityId());
                            List<HistoricActivityInstance> activityInstanceList = list.stream().filter(h -> h.getActivityId().equals(historicActivityInstance.getActivityId()) &&h.getEndTime()!=null).collect(Collectors.toList());
                            if(voList!=null){
                                collectUserTaskInfo(processInstanceComments, processInstanceAttachments, historicActivityInstance, voList, activityInstanceList);
                            }
                            else{
                                voList=new ArrayList<>();
                                collectUserTaskInfo(processInstanceComments, processInstanceAttachments, historicActivityInstance, voList, activityInstanceList);
                            }
                            deatailMap.put(historicActivityInstance.getActivityId(),voList);
                            endList.add(historicActivityInstance.getActivityId());
                        }
                        else if("serviceTask".equalsIgnoreCase(historicActivityInstance.getActivityType())){

                        }
                    }
                    else{
                        if ("userTask".equalsIgnoreCase(historicActivityInstance.getActivityType())){
                            List<TaskDetailVO> voList = deatailMap.get(historicActivityInstance.getActivityId());
                            List<HistoricActivityInstance> activityInstanceList = list.stream().filter(h -> h.getActivityId().equals(historicActivityInstance.getActivityId()) &&h.getEndTime()==null).collect(Collectors.toList());
                            if(voList!=null){
                                collectUserTaskInfo(processInstanceComments, processInstanceAttachments, historicActivityInstance, voList, activityInstanceList);
                            }
                            else{
                                voList=new ArrayList<>();
                                collectUserTaskInfo(processInstanceComments, processInstanceAttachments, historicActivityInstance, voList, activityInstanceList);
                            }
                            deatailMap.put(historicActivityInstance.getActivityId(),voList);
                            if(endList.contains(historicActivityInstance.getActivityId())){
                                endList.remove(historicActivityInstance.getActivityId());
                                runningList.add(historicActivityInstance.getActivityId());
                            }
                            else{
                                runningList.add(historicActivityInstance.getActivityId());
                            }
                        }
                        else if("serviceTask".equalsIgnoreCase(historicActivityInstance.getActivityType())){

                        }
                    }
                }
            }
            else{
                noTakeList.add(flowElement.getId());
            }
        }
        handleDataVO.setProcessTemplates(processTemplates);
        handleDataVO.setDetailVOList(deatailMap);
        return Result.OK(handleDataVO);
    }

    public Map<String,Object> getVariables(String processInstanceId){
        Map<String,Object> processVariables= new HashMap<>();
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        for (HistoricVariableInstance historicVariableInstance : list) {
            processVariables.put(historicVariableInstance.getName(),historicVariableInstance.getValue());
        }
        return processVariables;
    }

    private   String getCurrentName(String processInstanceId,Boolean flag,String processDefinitionId){
        if(flag){
            return "流程已结束";
        }
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask").finished().orderByHistoricActivityInstanceStartTime().desc().list();
        if(CollUtil.isEmpty(list)){
            return "";
        }
        else{
            HistoricActivityInstance historicActivityInstance = list.get(0);
            return historicActivityInstance.getActivityName();
        }
    }
}
