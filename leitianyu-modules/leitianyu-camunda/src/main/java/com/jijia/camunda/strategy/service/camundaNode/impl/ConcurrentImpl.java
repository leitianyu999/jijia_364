package com.jijia.camunda.strategy.service.camundaNode.impl;

import com.jijia.camunda.annotation.CamundaNodeTypeAnnotation;
import com.jijia.camunda.domain.dto.ChildNode;
import com.jijia.camunda.domain.enums.CamundaNodeType;
import com.jijia.camunda.strategy.service.camundaNode.abstractImpl.AbstractCamundaTypeStrategy;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author leitianyu
 */
@Component
@CamundaNodeTypeAnnotation(setNodeType = CamundaNodeType.CONCURRENT)
public class ConcurrentImpl extends AbstractCamundaTypeStrategy {


    @Override
    public String connect(AbstractFlowNodeBuilder<?, ?> builder, String eventId, ChildNode flowNode, List<SequenceFlow> sequenceFlows, Map<String, ChildNode> childNodeMap) {
        return "";
    }
}
