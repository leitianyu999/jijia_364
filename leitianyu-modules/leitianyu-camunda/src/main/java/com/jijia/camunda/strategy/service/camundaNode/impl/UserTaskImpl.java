package com.jijia.camunda.strategy.service.camundaNode.impl;

import com.jijia.camunda.annotation.CamundaNodeTypeAnnotation;
import com.jijia.camunda.domain.dto.ChildNode;
import com.jijia.camunda.domain.enums.CamundaNodeType;
import com.jijia.camunda.strategy.service.camundaNode.abstractImpl.AbstractCamundaTypeStrategy;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author leitianyu
 */
@Component
@CamundaNodeTypeAnnotation(setNodeType = CamundaNodeType.USER_TASK)
public class UserTaskImpl extends AbstractCamundaTypeStrategy {


    @Override
    public String connect(AbstractFlowNodeBuilder<?, ?> builder, String eventId, ChildNode flowNode, List<SequenceFlow> sequenceFlows, Map<String, ChildNode> childNodeMap) throws InvocationTargetException, IllegalAccessException {
        return super.taskConnect(builder, eventId, flowNode, sequenceFlows, childNodeMap);
    }


}
