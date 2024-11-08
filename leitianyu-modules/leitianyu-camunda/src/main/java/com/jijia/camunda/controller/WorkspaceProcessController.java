package com.jijia.camunda.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.model.v2.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jijia.camunda.dto.*;
import com.jijia.camunda.dto.json.ChildNode;
import com.jijia.camunda.dto.json.FormOperates;
import com.jijia.camunda.dto.json.SettingsInfo;
import com.jijia.camunda.dto.json.UserInfo;
import com.jijia.camunda.entity.ProcessTemplates;
import com.jijia.camunda.exception.WorkFlowException;
import com.jijia.camunda.service.ProcessTemplateService;
import com.jijia.camunda.vo.*;
import com.jijia.common.core.domain.R;
import com.jijia.common.core.web.page.PageDomain;
import com.jijia.common.core.web.page.TableSupport;
import com.jijia.common.log.annotation.Log;
import com.jijia.common.security.utils.SecurityUtils;
import com.jijia.system.api.model.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Attachment;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.DelegationState;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.jijia.camunda.utils.BpmnModelUtils.getChildNode;
import static com.jijia.camunda.utils.CommonConstants.*;
import static com.jijia.camunda.utils.WorkFlowConstants.COMMENT_SPLIT;
import static com.jijia.camunda.utils.WorkFlowConstants.PROCESS_PREFIX;
import static com.jijia.common.core.utils.PageUtils.startPage;

/**
 * @author : willian fu
 * @version : 1.0
 */
@RestController
@RequestMapping("/workspace")
@SuppressWarnings("all")
public class WorkspaceProcessController {

    @Resource
    private ProcessTemplateService processTemplateService;
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

    @ApiOperation("通过模板id查看流程信息 会附带流程定义id")
    @GetMapping("process/detail")
    public R<ProcessTemplates> detail(@RequestParam("templateId") String templateId){
        ProcessTemplates processTemplates = processTemplateService.getById(templateId);
        processTemplates.setFormId(processTemplates.getTemplateId());
        processTemplates.setFormName(processTemplates.getTemplateName());
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(PROCESS_PREFIX + templateId).latestVersion().singleResult();
        if(processDefinition==null){
            throw  new WorkFlowException("该流程暂未接入Camunda,请把这个流程图重新发布既可");
        }
        processTemplates.setProcessDefinitionId(processDefinition.getId());
        return R.ok(processTemplates);
    }


    @ApiOperation("通过流程定义id启动流程")
    @PostMapping("process/start")
    public R<Object> start(@RequestBody StartProcessInstanceDTO startProcessInstanceDTO){
        try{
            JSONObject formData = startProcessInstanceDTO.getFormData();
            Long startUserInfo = startProcessInstanceDTO.getStartUserId();
            identityService.setAuthenticatedUserId(String.valueOf(startUserInfo));
            Map<String,Object> processVariables= new HashMap<>();
            processVariables.put(FORM_VAR,formData);
            processVariables.put(PROCESS_STATUS,BUSINESS_STATUS_1);
            processVariables.put(APPLY_USER_ID,startUserInfo);
            processVariables.put(START_USER_INFO,startUserInfo);
            processVariables.put("root",startUserInfo);
            Map<String, List<Long>> processUsers = startProcessInstanceDTO.getProcessUsers();
            if(CollUtil.isNotEmpty(processUsers)){
                Set<String> strings = processUsers.keySet();
                for (String string : strings) {
                    List<Long> selectUserInfo = processUsers.get(string);
                    List<String> users=new ArrayList<>();
                    for (Long userInfo : selectUserInfo) {
                        users.add(userInfo.toString());
                    }
                    processVariables.put(string,users);
                }
            }

            Map formValue = JSONObject.parseObject(formData.toJSONString(), new TypeReference<Map>() {
            });
            processVariables.putAll(formValue);
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(startProcessInstanceDTO.getProcessDefinitionId(), processVariables);
            //手动完成第一个任务
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
            if(task!=null){
                taskService.complete(task.getId());
            }
            return R.ok(processInstance.getId());
        }
        catch (Exception e){
            Throwable cause = e.getCause();
            if(cause instanceof WorkFlowException){
                WorkFlowException workFlowException=(WorkFlowException)cause;
                return R.ok(workFlowException.getMessage());
            }
            e.printStackTrace();
            return R.fail("启动流程失败");
        }
    }

    public Map<String,Object> getVariables(String processInstanceId){
        Map<String,Object> processVariables= new HashMap<>();
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        for (HistoricVariableInstance historicVariableInstance : list) {
            processVariables.put(historicVariableInstance.getName(),historicVariableInstance.getValue());
        }
        return processVariables;
    }

    @ApiOperation("查看我发起的流程")
    @PostMapping("process/applyList")
    public R< Page<HistoryProcessInstanceVO>> applyList(@RequestBody ApplyDTO applyDTO){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<HistoricProcessInstance> historicProcessInstances =
                historyService.createHistoricProcessInstanceQuery()
                        .startedBy(applyDTO.getCurrentUserId().toString())
                        .orderByProcessInstanceStartTime().desc()
                        .listPage((pageDomain.getPageNum() - 1) * pageDomain.getPageSize(), pageDomain.getPageSize());
        long count = historyService.createHistoricProcessInstanceQuery()
                .startedBy(applyDTO.getCurrentUserId().toString()).count();
        List<Long> applyUserIds= new ArrayList<>();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            Map<String, Object> processVariables = getVariables(historicProcessInstance.getId());
            Long id = MapUtil.getLong(processVariables, START_USER_INFO);
            applyUserIds.add(id);
        }

        List<HistoryProcessInstanceVO> historyProcessInstanceVOS= new ArrayList<>();
        Page<HistoryProcessInstanceVO> page=new Page<>();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            Map<String, Object> processVariables = getVariables(historicProcessInstance.getId());
            HistoryProcessInstanceVO historyProcessInstanceVO=new HistoryProcessInstanceVO();
            historyProcessInstanceVO.setProcessInstanceId(historicProcessInstance.getId());
            historyProcessInstanceVO.setProcessDefinitionName(historicProcessInstance.getProcessDefinitionName());
            historyProcessInstanceVO.setStartUser(MapUtil.getLong(processVariables,START_USER_INFO));
            historyProcessInstanceVO.setUsers(historyProcessInstanceVO.getStartUser());
            historyProcessInstanceVO.setStartTime(historicProcessInstance.getStartTime());
            historyProcessInstanceVO.setEndTime(historicProcessInstance.getEndTime());
            Boolean flag= historicProcessInstance.getEndTime() != null;
            historyProcessInstanceVO.setCurrentActivityName(getCurrentName(historicProcessInstance.getId(),flag,historicProcessInstance.getProcessDefinitionId()));
            historyProcessInstanceVO.setBusinessStatus(MapUtil.getStr(processVariables,PROCESS_STATUS));


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
        page.setRecords(historyProcessInstanceVOS);
        page.setCurrent(pageDomain.getPageNum());
        page.setSize(pageDomain.getPageSize());
        page.setTotal(count);
        return R.ok(page);
    }





    private   String getCurrentName(String processInstanceId,Boolean flag,String processDefinitionId){
        if(flag){
            return "流程已结束";
        }
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
                .activityType("userTask").finished().orderByHistoricActivityInstanceStartTime().desc().list();
        if(CollUtil.isEmpty(list)){
            return "";
        }
        else{
            HistoricActivityInstance historicActivityInstance = list.get(0);
            return historicActivityInstance.getActivityName();
        }
    }

    @ApiOperation("查看我的待办")
    @PostMapping("process/toDoList")
    public R<Page<TaskVO>> toDoList(@RequestBody TaskDTO taskDTO){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(String.valueOf(taskDTO.getCurrentUserInfo()))
                .orderByTaskCreateTime().desc()
                .listPage((pageDomain.getPageNum() - 1) * pageDomain.getPageSize(), pageDomain.getPageSize());
        long count = taskService.createTaskQuery().taskAssignee(taskDTO.getCurrentUserInfo().toString()).count();
        List<TaskVO> taskVOS= new ArrayList<>();
        Page<TaskVO> page =new Page<>();


        List<Long> taskIds= new ArrayList<>();
        for (Task task : tasks) {
            Map<String, Object> processVariables = getVariables(task.getProcessInstanceId());
            Long id = MapUtil.getLong(processVariables, START_USER_INFO);
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
            taskVO.setStartUser(MapUtil.getLong(processVariables,START_USER_INFO));
            taskVO.setUsers(taskVO.getStartUser());
            taskVO.setStartTime(processInstance.getStartTime());
            taskVO.setCurrentActivityName(getCurrentName(processInstance.getId(),false,processInstance.getProcessDefinitionId()));

            taskVO.setBusinessStatus(MapUtil.getStr(processVariables,PROCESS_STATUS));
            taskVO.setTaskCreatedTime(task.getCreateTime());
            DelegationState delegationState = task.getDelegationState();
            if(delegationState!=null){
                taskVO.setDelegationState(delegationState);
            }
            taskVOS.add(taskVO);

        }
        page.setRecords(taskVOS);
        page.setCurrent(pageDomain.getPageNum());
        page.setSize(pageDomain.getPageSize());
        page.setTotal(count);
        return R.ok(page);
    }

    @ApiOperation("查看我的已办")
    @PostMapping("process/doneList")
    public R<Page<TaskVO>> doneList(@RequestBody TaskDTO taskDTO){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(taskDTO.getCurrentUserInfo().toString())
                .finished()
                .orderByHistoricActivityInstanceStartTime().desc()
                .listPage((pageDomain.getPageNum() - 1) * pageDomain.getPageSize(), pageDomain.getPageSize());
        long count = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(taskDTO.getCurrentUserInfo().toString()).finished().count();
        List<TaskVO> taskVOS= new ArrayList<>();
        Page<TaskVO> page =new Page<>();

        List<Long> taskIds= new ArrayList<>();
        for (HistoricTaskInstance task : tasks) {
            Map<String, Object> processVariables = getVariables(task.getProcessInstanceId());
            Long id = MapUtil.getLong(processVariables, START_USER_INFO);
            taskIds.add(id);
        }


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
            taskVO.setStartUser(MapUtil.getLong(processVariables,START_USER_INFO));
            taskVO.setUsers(taskVO.getStartUser());
            taskVO.setStartTime(historicProcessInstance.getStartTime());
            taskVO.setCurrentActivityName(getCurrentName(task.getProcessInstanceId(),flag,task.getProcessDefinitionId()));
            taskVO.setBusinessStatus(MapUtil.getStr(processVariables,PROCESS_STATUS));
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

        page.setRecords(taskVOS);
        page.setCurrent(pageDomain.getPageNum());
        page.setSize(pageDomain.getPageSize());
        page.setTotal(count);
        return R.ok(page);
    }



    @ApiOperation("同意按钮")
    @PostMapping("/agree")
    public R agree(@RequestBody HandleDataDTO handleDataDTO){
        Long currentUserInfo = SecurityUtils.getUserId();
        String comments = handleDataDTO.getComments();
        JSONObject formData = handleDataDTO.getFormData();
        String taskId = handleDataDTO.getTaskId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String,Object> map=new HashMap<>();
        if(formData!=null &&formData.size()>0){
            Map formValue = JSONObject.parseObject(formData.toJSONString(), new TypeReference<Map>() {
            });
            map.putAll(formValue);
            map.put(FORM_VAR,formData);
        }

        runtimeService.setVariables(task.getProcessInstanceId(),map);
        identityService.setAuthenticatedUserId(currentUserInfo.toString());
        if(StringUtils.isNotBlank(comments)){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"opinion"+COMMENT_SPLIT+comments);
        }
        if(StringUtils.isNotBlank(handleDataDTO.getSignInfo())){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"sign"+COMMENT_SPLIT+handleDataDTO.getSignInfo());
        }


        taskService.complete(task.getId());
        return R.ok();
    }

    @ApiOperation("委派按钮")
    @PostMapping("/delegateTask")
    public R delegateTask(@RequestBody HandleDataDTO handleDataDTO){
        Long currentUserInfo = handleDataDTO.getCurrentUserInfo();
        String comments = handleDataDTO.getComments();
        JSONObject formData = handleDataDTO.getFormData();
        String taskId = handleDataDTO.getTaskId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String,Object> map=new HashMap<>();
        if(formData!=null &&formData.size()>0){
            Map formValue = JSONObject.parseObject(formData.toJSONString(), new TypeReference<Map>() {
            });
            map.putAll(formValue);
            map.put(FORM_VAR,formData);
        }

        runtimeService.setVariables(task.getProcessInstanceId(),map);
        identityService.setAuthenticatedUserId(currentUserInfo.toString());
        if(StringUtils.isNotBlank(comments)){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"opinion"+COMMENT_SPLIT+comments);
        }

        if(StringUtils.isNotBlank(handleDataDTO.getSignInfo())){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"sign"+COMMENT_SPLIT+handleDataDTO.getSignInfo());
        }

        Long delegateUserInfo = handleDataDTO.getDelegateUserInfo();
        taskService.delegateTask(task.getId(),delegateUserInfo.toString());
        return R.ok();
    }

    @ApiOperation("委派人完成的按钮")
    @PostMapping("/resolveTask")
    public R resolveTask(@RequestBody HandleDataDTO handleDataDTO){
        Long currentUserInfo = handleDataDTO.getCurrentUserInfo();
        String comments = handleDataDTO.getComments();
        JSONObject formData = handleDataDTO.getFormData();
        String taskId = handleDataDTO.getTaskId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String,Object> map=new HashMap<>();
        if(formData!=null &&formData.size()>0){
            Map formValue = JSONObject.parseObject(formData.toJSONString(), new TypeReference<Map>() {
            });
            map.putAll(formValue);
            map.put(FORM_VAR,formData);
        }

        runtimeService.setVariables(task.getProcessInstanceId(),map);
        identityService.setAuthenticatedUserId(currentUserInfo.toString());
        if(StringUtils.isNotBlank(comments)){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"opinion"+COMMENT_SPLIT+comments);
        }

        if(StringUtils.isNotBlank(handleDataDTO.getSignInfo())){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"sign"+COMMENT_SPLIT+handleDataDTO.getSignInfo());
        }

        taskService.resolveTask(taskId);
        return R.ok();
    }


    @ApiOperation("拒绝按钮")
    @PostMapping("/refuse")
    public R  refuse(@RequestBody HandleDataDTO handleDataDTO){
        Long currentUserInfo = handleDataDTO.getCurrentUserInfo();
        identityService.setAuthenticatedUserId(currentUserInfo.toString());
        String comments = handleDataDTO.getComments();
        JSONObject formData = handleDataDTO.getFormData();
        String taskId = handleDataDTO.getTaskId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String,Object> map=new HashMap<>();
        if(formData!=null &&formData.size()>0){
            Map formValue = JSONObject.parseObject(formData.toJSONString(), new TypeReference<Map>() {
            });
            map.putAll(formValue);
            map.put(FORM_VAR,formData);
        }
        map.put(PROCESS_STATUS,BUSINESS_STATUS_3);
        runtimeService.setVariables(task.getProcessInstanceId(),map);
        if(StringUtils.isNotBlank(comments)){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"opinion"+COMMENT_SPLIT+comments);
        }

        if(StringUtils.isNotBlank(handleDataDTO.getSignInfo())){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"sign"+COMMENT_SPLIT+handleDataDTO.getSignInfo());
        }
        runtimeService.deleteProcessInstance(task.getProcessInstanceId(),"拒绝");
        return R.ok();
    }

    @ApiOperation("撤销按钮")
    @PostMapping("/revoke")
    public R revoke(@RequestBody HandleDataDTO handleDataDTO){
        Long currentUserInfo = handleDataDTO.getCurrentUserInfo();
        identityService.setAuthenticatedUserId(currentUserInfo.toString());
        String comments = handleDataDTO.getComments();
        JSONObject formData = handleDataDTO.getFormData();
        String taskId = handleDataDTO.getTaskId();
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        Map<String,Object> map=new HashMap<>();
        if(formData!=null &&formData.size()>0){
            Map formValue = JSONObject.parseObject(formData.toJSONString(), new TypeReference<Map>() {
            });
            map.putAll(formValue);
            map.put(FORM_VAR,formData);
        }
        map.put(PROCESS_STATUS,BUSINESS_STATUS_2);
        runtimeService.setVariables(task.getProcessInstanceId(),map);
        if(StringUtils.isNotBlank(comments)){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"opinion"+COMMENT_SPLIT+comments);
        }

        if(StringUtils.isNotBlank(handleDataDTO.getSignInfo())){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"sign"+COMMENT_SPLIT+handleDataDTO.getSignInfo());
        }
        runtimeService.deleteProcessInstance(task.getProcessInstanceId(),"撤销");
        return R.ok();
    }


    @ApiOperation("转办按钮")
    @PostMapping("/assignee")
    public R assignee(@RequestBody HandleDataDTO handleDataDTO){
        Long currentUserInfo = handleDataDTO.getCurrentUserInfo();
        identityService.setAuthenticatedUserId(currentUserInfo.toString());
        String comments = handleDataDTO.getComments();
        JSONObject formData = handleDataDTO.getFormData();
        String taskId = handleDataDTO.getTaskId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String,Object> map=new HashMap<>();
        if(formData!=null &&formData.size()>0){
            Map formValue = JSONObject.parseObject(formData.toJSONString(), new TypeReference<Map>() {
            });
            map.putAll(formValue);
            map.put(FORM_VAR,formData);
        }
        map.put(PROCESS_STATUS,BUSINESS_STATUS_1);
        runtimeService.setVariables(task.getProcessInstanceId(),map);
        if(StringUtils.isNotBlank(comments)){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"opinion"+COMMENT_SPLIT+comments);
        }

        if(StringUtils.isNotBlank(handleDataDTO.getSignInfo())){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"sign"+COMMENT_SPLIT+handleDataDTO.getSignInfo());
        }
        taskService.setAssignee(taskId,handleDataDTO.getTransferUserInfo().toString());
        return R.ok();
    }
    @ApiOperation("查询可退回的节点(这个是给 下面 rollback接口作为入参用的 )")
    @PostMapping("/rollbackNodes")
    public R rollbackNodes(@RequestBody HandleDataDTO handleDataDTO){
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().finished().processInstanceId(handleDataDTO.getProcessInstanceId()).list();
        Map<String,String> nodes=new HashMap<>();
        for (HistoricActivityInstance activityInstance : list) {
            nodes.put(activityInstance.getActivityId(),activityInstance.getActivityName());
        }
        return R.ok(nodes);
    }

    @ApiOperation("退回按钮")
    @PostMapping("/rollback")
    public R rollback(@RequestBody HandleDataDTO handleDataDTO){
        Long currentUserInfo = handleDataDTO.getCurrentUserInfo();
        identityService.setAuthenticatedUserId(currentUserInfo.toString());
        String comments = handleDataDTO.getComments();
        JSONObject formData = handleDataDTO.getFormData();
        String taskId = handleDataDTO.getTaskId();
        String processInstanceId = handleDataDTO.getProcessInstanceId();
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        Task task = null;
        List<String> taskIds = new ArrayList<>();

        for (Task task1 : list) {
            if(task1.getId().equals(taskId)){
                task=task1;
            }
            taskIds.add(task1.getTaskDefinitionKey());
        }



        Map<String,Object> map=new HashMap<>();
        if(formData!=null &&formData.size()>0){
            Map formValue = JSONObject.parseObject(formData.toJSONString(), new TypeReference<Map>() {
            });
            map.putAll(formValue);
            map.put(FORM_VAR,formData);
        }
        map.put(PROCESS_STATUS,BUSINESS_STATUS_3);
        runtimeService.setVariables(task.getProcessInstanceId(),map);
        if(StringUtils.isNotBlank(comments)){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"opinion"+COMMENT_SPLIT+comments);
        }

        if(StringUtils.isNotBlank(handleDataDTO.getSignInfo())){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"sign"+COMMENT_SPLIT+handleDataDTO.getSignInfo());
        }

            //调用modify api  当然 因为本接口没有前端联调 就暂时不做了
        return R.ok();
    }


    @ApiOperation("加签按钮")
    @PostMapping("/addMulti")
    public R addMulti(@RequestBody HandleDataDTO handleDataDTO){
        Long currentUserInfo = handleDataDTO.getCurrentUserInfo();
        identityService.setAuthenticatedUserId(currentUserInfo.toString());
        String comments = handleDataDTO.getComments();
        JSONObject formData = handleDataDTO.getFormData();
        String taskId = handleDataDTO.getTaskId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String,Object> map=new HashMap<>();
        if(formData!=null &&formData.size()>0){
            Map formValue = JSONObject.parseObject(formData.toJSONString(), new TypeReference<Map>() {
            });
            map.putAll(formValue);
            map.put(FORM_VAR,formData);
        }
        map.put(PROCESS_STATUS,BUSINESS_STATUS_1);
        runtimeService.setVariables(task.getProcessInstanceId(),map);
        if(StringUtils.isNotBlank(comments)){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"opinion"+COMMENT_SPLIT+comments);
        }

        if(StringUtils.isNotBlank(handleDataDTO.getSignInfo())){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"sign"+COMMENT_SPLIT+handleDataDTO.getSignInfo());
        }

        Map<String,Object> variableMap= new HashMap<>();
        variableMap.put("assigneeName",handleDataDTO.getMultiAddUserInfo().toString());
        runtimeService.createProcessInstanceModification(task.getProcessInstanceId())
                .startBeforeActivity(task.getTaskDefinitionKey())
                .execute();
        return R.ok();
    }


    @ApiOperation("查到签上的人")
    @PostMapping("/queryMultiUsersInfo")
    public R<List<MultiVO>> queryMultiUsersInfo(@RequestBody Map<String,Object> map){
        String taskId = MapUtil.getStr(map, "taskId");
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        List<Task> list = taskService.createTaskQuery()
            .processInstanceId(task.getProcessInstanceId())
            .taskDefinitionKey(task.getTaskDefinitionKey()).list();
        Iterator<Task> iterator = list.iterator();
        List<MultiVO> multiVOList= new ArrayList<>();
        while (iterator.hasNext()){
            Task next = iterator.next();
            if(!taskId.equals(next.getId())){
                MultiVO multiVO=new MultiVO();
                multiVO.setTaskId(next.getId());
                multiVO.setProcessInstanceId(next.getProcessInstanceId());
                multiVO.setExecutionId(next.getExecutionId());
                multiVO.setUserId(Long.valueOf(next.getAssignee()));
                multiVOList.add(multiVO);
            }

        }
        return R.ok(multiVOList);
    }

    @ApiOperation("减签按钮")
    @PostMapping("/deleteMulti")
    public R deleteMulti(@RequestBody List<String> executionIds){
        for (String executionId : executionIds) {
            Task task = taskService.createTaskQuery().executionId(executionId).singleResult();
            String activanceId=task.getTaskDefinitionKey()+":"+executionId;
            runtimeService.createProcessInstanceModification(task.getProcessInstanceId())
                    .cancelActivityInstance(activanceId)
                    .execute();
        }

        return R.ok();
    }


    @ApiOperation("评论按钮")
    @PostMapping("/comments")
    public R comments(@RequestBody HandleDataDTO handleDataDTO){
        Long currentUserInfo = handleDataDTO.getCurrentUserInfo();
        identityService.setAuthenticatedUserId(currentUserInfo.toString());
        String comments = handleDataDTO.getComments();
        JSONObject formData = handleDataDTO.getFormData();
        String taskId = handleDataDTO.getTaskId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String,Object> map=new HashMap<>();
        if(formData!=null &&formData.size()>0){
            Map formValue = JSONObject.parseObject(formData.toJSONString(), new TypeReference<Map>() {
            });
            map.putAll(formValue);
            map.put(FORM_VAR,formData);
        }
        map.put(PROCESS_STATUS,BUSINESS_STATUS_1);
        runtimeService.setVariables(task.getProcessInstanceId(),map);
        if(StringUtils.isNotBlank(comments)){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"comments"+COMMENT_SPLIT+comments);
        }

        if(StringUtils.isNotBlank(handleDataDTO.getSignInfo())){
            taskService.addComment(task.getId(),task.getProcessInstanceId(),"sign"+COMMENT_SPLIT+handleDataDTO.getSignInfo());
        }
        return R.ok();
    }

    @ApiOperation("通过流程实例id查看详情")
    @PostMapping("process/instanceInfo")
    public R<HandleDataVO> instanceInfo(@RequestBody HandleDataDTO HandleDataDTO){
        String processInstanceId = HandleDataDTO.getProcessInstanceId();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
                .singleResult();
        String processDefinitionKey = historicProcessInstance.getProcessDefinitionKey();
        ProcessTemplates processTemplates = processTemplateService.getById(processDefinitionKey.replace(PROCESS_PREFIX,""));
        processTemplates.setFormId(processTemplates.getTemplateId());
        processTemplates.setFormName(processTemplates.getTemplateName());
        processTemplates.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());

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
        return R.ok(handleDataVO);
    }

    private void collectUserTaskInfo(List<Comment> processInstanceComments,
                                     List<Attachment> processInstanceAttachments,
                                     HistoricActivityInstance historicActivityInstance,
                                     List<TaskDetailVO> voList,
                                     List<HistoricActivityInstance> activityInstanceList) {
        for (HistoricActivityInstance activityInstance : activityInstanceList) {
            TaskDetailVO taskDetailVO =new TaskDetailVO();
            taskDetailVO.setTaskId(activityInstance.getTaskId());
            taskDetailVO.setActivityId(activityInstance.getActivityId());
            taskDetailVO.setName(activityInstance.getActivityName());
            taskDetailVO.setCreateTime(activityInstance.getStartTime());
            taskDetailVO.setEndTime(activityInstance.getEndTime());
            Comment signComment = processInstanceComments.stream().filter(h -> h.getTaskId().equals(historicActivityInstance.getTaskId()) && h.getFullMessage().split(COMMENT_SPLIT)[0].equals("sign")).findFirst().orElse(null);
            if(signComment!=null){
                taskDetailVO.setSignImage(signComment.getFullMessage());
            }
            List<Attachment> attachments = processInstanceAttachments.stream().filter(h -> h.getTaskId().equals(historicActivityInstance.getTaskId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(attachments)){
                List<AttachmentVO> attachmentVOList = new ArrayList<>();
                for (Attachment attachment : attachments) {
                    AttachmentVO attachmentVO = new AttachmentVO();
                    attachmentVO.setId(attachment.getId());
                    attachmentVO.setName(attachment.getName());
                    attachmentVO.setUrl(attachment.getUrl());
                    attachmentVOList.add(attachmentVO);
                }
                taskDetailVO.setAttachmentVOList(attachmentVOList);
            }

            List<Comment> options = processInstanceComments.stream().filter(h -> h.getTaskId().equals(historicActivityInstance.getTaskId()) && h.getFullMessage().split(COMMENT_SPLIT)[0].equals("opinion")).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(options)){
                List<OptionVO> optionVOList =new ArrayList<>();
                for (Comment option : options) {
                    OptionVO optionVO = new OptionVO();
                    optionVO.setComments(option.getFullMessage());
                    optionVO.setUserId(option.getUserId());
//                                        optionVO.setUserName();
                    optionVO.setCreateTime(option.getTime());
                    optionVOList.add(optionVO);
                }
                taskDetailVO.setOptionVOList(optionVOList);
            }

            List<Comment> comments = processInstanceComments.stream().filter(h -> h.getTaskId().equals(historicActivityInstance.getTaskId()) && h.getFullMessage().split(COMMENT_SPLIT)[0].equals("comments")).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(comments)){
                List<CommentVO> commentsVOList =new ArrayList<>();
                for (Comment comment : comments) {
                    CommentVO commentVO = new CommentVO();
                    commentVO.setComments(comment.getFullMessage());
                    commentVO.setUserId(comment.getUserId());
//                                        commentVO.setUserName();
                    commentVO.setCreateTime(comment.getTime());
                    commentsVOList.add(commentVO);
                }
                taskDetailVO.setCommentVOList(commentsVOList);
            }

            voList.add(taskDetailVO);



        }
    }





}