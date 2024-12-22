package com.jijia.camunda.strategy.service.camundaNode;


import com.jijia.camunda.domain.dto.ChildNode;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.EndEventBuilder;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 台账生成接口
 *
 * @author leitianyu
 */
public interface CamundaNodeStrategy {


    public String connect(AbstractFlowNodeBuilder<?, ?> builder,
                   String eventId,
                   ChildNode flowNode,
                   List<SequenceFlow> sequenceFlows,
                   Map<String,ChildNode> childNodeMap) throws InvocationTargetException, IllegalAccessException;

    public void connect(AbstractFlowNodeBuilder<?, ?> builder,
                          String eventId,
                          EndEventBuilder endEventBuilder,
                          List<SequenceFlow> sequenceFlows,
                          Map<String,ChildNode> childNodeMap) throws InvocationTargetException, IllegalAccessException;

    public void afterConnectByFrom(AbstractFlowNodeBuilder<?, ?> cmdNode,
                                   AbstractFlowNodeBuilder<?, ?> toNode,
                                   String from,
                                   String to,
                                   List<SequenceFlow> sequenceFlows,
                                   Map<String, ChildNode> childNodeMap,
                                   SequenceFlow sequenceFlow,
                                   AbstractFlowNodeBuilder<?, ?> paramNode,
                                   String  sequenceFlowId);


}
