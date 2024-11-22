package com.jijia.camunda.utils;

import com.google.common.collect.Lists;
import com.jijia.camunda.constants.CamundaWorkConstants;
import com.jijia.camunda.domain.CmdModel;
import com.jijia.camunda.domain.dto.ChildNode;
import com.jijia.camunda.domain.dto.CmdModelDto;
import com.jijia.camunda.domain.enums.CamundaNodeType;
import com.jijia.camunda.strategy.handler.HandlerCamundaNodeContext;
import com.jijia.camunda.strategy.handler.HandlerModelContext;
import com.jijia.camunda.strategy.service.camundaNode.CamundaNodeStrategy;
import com.jijia.common.core.exception.GlobalException;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.EndEventBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.StartEventBuilder;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/11/21
 */
public class BpmnUtils {

    public static byte[] getBpmnXmlByJson(CmdModelDto cmdModelDto, CmdModel flowNode, Long oilId) {

        // 流程构造器
        ProcessBuilder process = Bpmn.createExecutableProcess();
        // 连线
        List<SequenceFlow> sequenceFlowList = Lists.newArrayList();
        // 节点
        Map<String, ChildNode> childNodeMap=new HashMap<>();

        process.id(CamundaWorkConstants.PROCESS_PREFIX + oilId);
        process.name(flowNode.getName());
        process.documentation(flowNode.getDescription());

        // 获取开始事件构造器
        StartEventBuilder startEvent = createStartEvent(process);
        String lastNode;
        try {
            lastNode = create(startEvent, CamundaWorkConstants.START_EVENT_ID,cmdModelDto.getChildNode(),sequenceFlowList,childNodeMap);
        } catch (Exception e) {
            throw new GlobalException("节点生成失败：" + e.getMessage());
        }
//        AbstractFlowNodeBuilder<?, ?> fromBuilder = moveToNode(startEvent, lastNode);
//        EndEventBuilder endEventBuilder = fromBuilder.endEvent(END_EVENT_ID).camundaExecutionListenerDelegateExpression(ExecutionListener.EVENTNAME_END, "${processListener}");
//        connect(fromBuilder,endEventBuilder,lastNode, END_EVENT_ID,sequenceFlows,childNodeMap);
        BpmnModelInstance modelInstance = process.done();

        // 获取字节流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Bpmn.writeModelToStream(outputStream, modelInstance);
        return outputStream.toByteArray();

    }

    public static StartEventBuilder createStartEvent(ProcessBuilder executableProcess) {
        StartEventBuilder startEventBuilder = executableProcess.startEvent();
        startEventBuilder.id(CamundaWorkConstants.START_EVENT_ID);
        startEventBuilder.camundaInitiator("applyUserId");
        return startEventBuilder;
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

}
