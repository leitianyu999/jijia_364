package com.jijia.camunda.strategy.service.camundaNode.abstractImpl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.jijia.camunda.domain.dto.ChildNode;
import com.jijia.camunda.domain.dto.Properties;
import com.jijia.camunda.domain.enums.CamundaNodeType;
import com.jijia.camunda.enums.ModeEnums;
import com.jijia.camunda.strategy.handler.HandlerCamundaNodeContext;
import com.jijia.camunda.strategy.service.camundaNode.CamundaNodeStrategy;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.MultiInstanceLoopCharacteristicsBuilder;
import org.camunda.bpm.model.bpmn.builder.UserTaskBuilder;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author leitianyu
 */
@Component
public abstract class AbstractCamundaTypeStrategy implements CamundaNodeStrategy {

    @Override
    public void afterConnectByFrom(AbstractFlowNodeBuilder<?, ?> cmdNode,
                                   AbstractFlowNodeBuilder<?, ?> toNode,
                                   String from,
                                   String to,
                                   List<SequenceFlow> sequenceFlows,
                                   Map<String, ChildNode> childNodeMap,
                                   SequenceFlow sequenceFlow,
                                   AbstractFlowNodeBuilder<?, ?> paramNode,
                                   String  sequenceFlowId) {

        sequenceFlow.setId(sequenceFlowId);
        sequenceFlows.add(sequenceFlow);

    }

    protected static AbstractFlowNodeBuilder<?, ?> moveToNode(AbstractFlowNodeBuilder<?, ?> startEventBuilder, String identifier) {
        return startEventBuilder.moveToNode(identifier);
    }

    protected static String id(String prefix) {
        return prefix + "_" + UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    protected static SequenceFlow connect(AbstractFlowNodeBuilder<?, ?> cmdNode,
                                          AbstractFlowNodeBuilder<?, ?> toNode,
                                          String from,
                                          String to,
                                          List<SequenceFlow> sequenceFlows,
                                          Map<String, ChildNode> childNodeMap) {
        // 连线ID
        String  sequenceFlowId = id("sequenceFlow");
        // 跳到当前节点
        AbstractFlowNodeBuilder<?, ?> paramNode = moveToNode(cmdNode, from);
        BpmnModelInstance bpmnModelInstance = paramNode.done();
        FlowNode element = (FlowNode) paramNode.getElement();
        // 是否已经连线
        SequenceFlow sequenceFlow = iteratorSequenceFlow(element, from, to);
        if(sequenceFlow==null){
            paramNode.connectTo(to);
            sequenceFlow= iteratorSequenceFlow(element, from, to);
        }

        if(bpmnModelInstance.getModelElementById(from) !=null) {
            CamundaNodeStrategy instance = HandlerCamundaNodeContext.getInstance(CamundaNodeType.getByTypeClass(bpmnModelInstance.getModelElementById(from).getClass()));
            instance.afterConnectByFrom(cmdNode, toNode, from, to, sequenceFlows, childNodeMap, sequenceFlow, paramNode, sequenceFlowId);
        }


        return null;
    }

    private static SequenceFlow iteratorSequenceFlow(FlowNode element, String from, String to) {
        Collection<SequenceFlow> outgoing = element.getOutgoing();
        SequenceFlow resultSequenceFlow=null;
        for (SequenceFlow sequenceFlow : outgoing) {
            if((ObjectUtil.isNotEmpty(sequenceFlow.getSource()) && sequenceFlow.getSource().getId().equals(from))
                    && (ObjectUtil.isNotEmpty(sequenceFlow.getTarget()) && sequenceFlow.getTarget().getId().equals(to))
            ){
                resultSequenceFlow=sequenceFlow;
                break;
            }
        }
        return resultSequenceFlow;
    }

    protected static String connectNextNode(AbstractFlowNodeBuilder<?, ?> builder,
                                         String identifier,
                                         ChildNode nextNode,
                                         List<SequenceFlow> sequenceFlows,
                                         Map<String, ChildNode> childNodeMap) throws InvocationTargetException, IllegalAccessException {
        if (Objects.nonNull(nextNode) && StringUtils.isNotBlank(nextNode.getId())) {
            AbstractFlowNodeBuilder<?, ?> abstractFlowNodeBuilder = moveToNode(builder, identifier);
            CamundaNodeStrategy instance = HandlerCamundaNodeContext.getInstance(CamundaNodeType.valueOf(nextNode.getType()));
            return instance.connect(abstractFlowNodeBuilder, identifier, nextNode, sequenceFlows, childNodeMap);
        } else {
            return identifier;
        }
    }

    protected static String createTask(AbstractFlowNodeBuilder startFlowNodeBuilder,
                                     ChildNode flowNode,
                                     List<SequenceFlow> sequenceFlows,
                                     Map<String, ChildNode> childNodeMap) throws InvocationTargetException, IllegalAccessException {

        // 获取当前任务的入口
        JSONObject incomingJson = flowNode.getIncoming();
        List<String> incoming = incomingJson.getJSONArray("incoming").toJavaList(String.class);

        String id = flowNode.getId();
        if (incoming != null && !incoming.isEmpty()) {
            // 创建 serviceTask
            AbstractFlowNodeBuilder<?, ?> abstractFlowNodeBuilder = moveToNode(startFlowNodeBuilder, incoming.get(0));
            // 自动生成id
            Method createTarget = getDeclaredMethod(abstractFlowNodeBuilder, "createTarget", Class.class);
            // 手动传入id
            Objects.requireNonNull(createTarget).setAccessible(true);
            Object target = createTarget.invoke(abstractFlowNodeBuilder, UserTask.class);
            UserTask userTask = (UserTask) target;
            userTask.setName(flowNode.getName());
            userTask.setId(id);
            UserTaskBuilder builder = userTask.builder();
            connect(startFlowNodeBuilder, builder, incoming.get(0), id, sequenceFlows, childNodeMap);
            builder.camundaTaskListenerDelegateExpression(TaskListener.EVENTNAME_CREATE, "${taskCreatedListener}");

            if ("root".equalsIgnoreCase(id)) {
                userTask.setCamundaAssignee("${applyUserId}");
            } else {
                Properties props = flowNode.getProps();
                String mode = props.getMode();
                MultiInstanceLoopCharacteristicsBuilder multiInstanceLoopCharacteristics = builder.multiInstance().camundaCollection("${camundaFlowUtils.calculateTaskCandidateUsers(execution)}").camundaElementVariable("assignee");
                if (ModeEnums.OR.getTypeName().equals(mode)) {
                    multiInstanceLoopCharacteristics.completionCondition("${nrOfCompletedInstances/nrOfInstances > 0}");
                } else if (ModeEnums.NEXT.getTypeName().equals(mode)) {
                    multiInstanceLoopCharacteristics.sequential();
                }
                userTask.setCamundaAssignee("${assignee}");
                JSONObject timeLimit = props.getTimeLimit();
                if (timeLimit != null && !timeLimit.isEmpty()) {
                    JSONObject timeout = timeLimit.getJSONObject("timeout");
                    if (timeout != null && !timeout.isEmpty()) {
//                        String unit = timeout.getString("unit");
                        Integer value = timeout.getInteger("value");
                        if (value > 0) {
                            //超时自动通过待补充 ,也许会换另一种方式
                        }
                    }
                }

            }
        }
        return id;
    }

    public String taskConnect(AbstractFlowNodeBuilder<?, ?> builder, String eventId, ChildNode flowNode, List<SequenceFlow> sequenceFlows, Map<String, ChildNode> childNodeMap) throws InvocationTargetException, IllegalAccessException {
        childNodeMap.put(flowNode.getId(), flowNode);
        JSONObject incoming = flowNode.getIncoming();
        incoming.put("incoming", Collections.singletonList(eventId));
        String id = createTask(builder, flowNode, sequenceFlows, childNodeMap);
        // 如果当前任务还有后续任务，则遍历创建后续任务
        ChildNode children = flowNode.getChildren();
        return connectNextNode(builder, id, children, sequenceFlows, childNodeMap);
    }


    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @return 父类中的方法对象
     */
    protected static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception ignore) {
            }
        }
        return null;
    }

}
