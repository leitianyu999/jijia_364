package com.jijia.camunda.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.jijia.camunda.constants.CamundaWorkConstants;
import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.dto.ChildNode;
import com.jijia.camunda.domain.dto.CmdModelDto;
import com.jijia.camunda.domain.dto.HandleDataDTO;
import com.jijia.camunda.domain.enums.CamundaNodeType;
import com.jijia.camunda.strategy.handler.HandlerCamundaNodeContext;
import com.jijia.camunda.strategy.handler.HandlerModelContext;
import com.jijia.camunda.strategy.service.camundaNode.CamundaNodeStrategy;
import com.jijia.common.core.exception.GlobalException;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.EndEventBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.StartEventBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.Model;
import org.camunda.bpm.spring.boot.starter.event.TaskEvent;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/11/21
 */
public class BpmnUtils {

    public static BpmnModelInstance getBpmnXmlByJson(CmdModel flowNode) {

        // 流程构造器
        ProcessBuilder process = Bpmn.createExecutableProcess();
        // 连线
        List<SequenceFlow> sequenceFlowList = Lists.newArrayList();
        // 节点
        Map<String, ChildNode> childNodeMap=new HashMap<>();

        process.id(CamundaWorkConstants.PROCESS_PREFIX + flowNode.getCode());
        process.name(flowNode.getName());
        process.documentation(flowNode.getDescription());

        // 获取开始事件构造器
        StartEventBuilder startEvent = createStartEvent(process);
        String lastNode;
        if (StringUtils.isBlank(flowNode.getNodeJsonData())){
            throw new GlobalException("节点数据为空");
        }
        ChildNode childNode = JSONObject.parseObject(flowNode.getNodeJsonData(), new TypeReference<ChildNode>(){});
        try {
            lastNode = create(startEvent, CamundaWorkConstants.START_EVENT_ID,childNode,sequenceFlowList,childNodeMap);
        } catch (Exception e) {
            throw new GlobalException("节点生成失败：" + e.getMessage());
        }

        // 设置事件结束后的处理
        AbstractFlowNodeBuilder<?, ?> fromBuilder = moveToNode(startEvent, lastNode);
        EndEventBuilder endEventBuilder = fromBuilder.endEvent(CamundaWorkConstants.END_EVENT_ID).camundaExecutionListenerDelegateExpression(ExecutionListener.EVENTNAME_END, "${processListener}");

        // 连接尾节点和结束节点
        ChildNode lastNodeMsg = getChildNode(childNode, lastNode);
        try {
            HandlerCamundaNodeContext.getInstance(CamundaNodeType.valueOf(lastNodeMsg.getType()))
                    .connect(fromBuilder, lastNode, endEventBuilder, sequenceFlowList, childNodeMap);
        } catch (Exception e) {
            throw new GlobalException("节点生成失败：" + e.getMessage());
        }


        BpmnModelInstance modelInstance = process.done();

        // 获取字节流
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        Bpmn.writeModelToStream(outputStream, modelInstance);
        return modelInstance;

    }

    public static StartEventBuilder createStartEvent(ProcessBuilder executableProcess) {
        StartEventBuilder startEventBuilder = executableProcess.startEvent();
        startEventBuilder.id(CamundaWorkConstants.START_EVENT_ID);
        startEventBuilder.camundaInitiator("applyUserId");
        return startEventBuilder;
    }

    protected static AbstractFlowNodeBuilder<?, ?> moveToNode(AbstractFlowNodeBuilder<?, ?> startEventBuilder, String identifier) {
        return startEventBuilder.moveToNode(identifier);
    }


    public static String create(AbstractFlowNodeBuilder abstractFlowNodeBuilder,
                                String eventId,
                                ChildNode flowNode,
                                List<SequenceFlow> sequenceFlows,
                                Map<String,ChildNode> childNodeMap) throws InvocationTargetException, IllegalAccessException {

        // 获取节点类型
        String nodeType = flowNode.getType();
        // 获取节点处理策略
        CamundaNodeStrategy instance = HandlerCamundaNodeContext.getInstance(CamundaNodeType.valueOf(nodeType));


        return instance.connect(abstractFlowNodeBuilder,eventId,flowNode,sequenceFlows,childNodeMap);

    }

    public static  ChildNode getChildNode(ChildNode childNode,String nodeId){

        if (childNode==null){
            return null;
        }

        if (StringUtils.isNotBlank(childNode.getId()) && childNode.getId().equals(nodeId)){
            return childNode;
        }

        if (childNode.getBranchs()!=null && !childNode.getBranchs().isEmpty()){
            for (ChildNode branch : childNode.getBranchs()) {
                if (StringUtils.isNotBlank(branch.getId()) && branch.getId().equals(nodeId)){
                    return branch;
                }
                if (branch.getChildren()!=null){
                    ChildNode result = getChildNode(branch.getChildren(), nodeId);
                    if (result!=null && StringUtils.isNotBlank(result.getId())){
                        return result;
                    }
                }
            }
        }

        if (childNode.getChildren()!=null && StringUtils.isNotBlank(childNode.getChildren().getId())){
            ChildNode result = getChildNode(childNode.getChildren(), nodeId);
            if (result!=null && StringUtils.isNotBlank(result.getId())){
                return result;
            }
        }
        return null;
    }


    public static void validateBpmn(BpmnModelInstance xml) {
        // 不为null
        if (xml == null) {
            throw new GlobalException("BPMN 模型不能为空");
        }
        // 1. 没有 StartEvent
        StartEvent startEvent = getStartEvent(xml);
        if (startEvent == null) {
            throw new GlobalException("BPMN 模型必须有一个 StartEvent");
        }
        // 2. 校验 UserTask 的 name 都配置了
        List<UserTask> userTasks = getBpmnModelElements(xml, UserTask.class);
        userTasks.forEach(userTask -> {
            if (StrUtil.isEmpty(userTask.getName())) {
                throw new GlobalException("BPMN 模型的 UserTask 必须配置 name：" + userTask.getId());
            }
        });
    }

    public static <T extends FlowNode> List<T> getBpmnModelElements(BpmnModelInstance model, Class<T> clazz) {
        return new ArrayList<>(model.getModelElementsByType(clazz));
    }

    private static StartEvent getStartEvent(BpmnModelInstance xml) {
        Collection<StartEvent> modelElementsByType = xml.getModelElementsByType(StartEvent.class);
        if (CollUtil.isEmpty(modelElementsByType)) {
            return null;
        }
        if (modelElementsByType.size() > 1) {
            throw new GlobalException("BPMN 模型只能有一个 StartEvent");
        }
        return modelElementsByType.iterator().next();
    }


}
