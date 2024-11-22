package com.jijia.camunda.strategy.service.camundaNode.impl;

import com.alibaba.fastjson.JSONObject;
import com.jijia.camunda.annotation.CamundaNodeTypeAnnotation;
import com.jijia.camunda.domain.dto.*;
import com.jijia.camunda.domain.enums.CamundaNodeType;
import com.jijia.camunda.enums.ModeEnums;
import com.jijia.camunda.strategy.service.camundaNode.abstractImpl.AbstractCamundaTypeStrategy;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.MultiInstanceLoopCharacteristicsBuilder;
import org.camunda.bpm.model.bpmn.builder.UserTaskBuilder;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author leitianyu
 */
@Component
@CamundaNodeTypeAnnotation(setNodeType = CamundaNodeType.USER_TASK)
public class UserTaskImpl extends AbstractCamundaTypeStrategy {


    @Override
    public String connect(AbstractFlowNodeBuilder<?, ?> builder, String eventId, ChildNode flowNode, List<SequenceFlow> sequenceFlows, Map<String, ChildNode> childNodeMap) throws InvocationTargetException, IllegalAccessException {
        return super.taskConnect(builder,eventId,flowNode,sequenceFlows,childNodeMap);
    }



}
