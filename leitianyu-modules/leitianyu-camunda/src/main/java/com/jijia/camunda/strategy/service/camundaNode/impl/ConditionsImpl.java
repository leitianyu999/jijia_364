package com.jijia.camunda.strategy.service.camundaNode.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jijia.camunda.annotation.CamundaNodeTypeAnnotation;
import com.jijia.camunda.constants.CamundaWorkConstants;
import com.jijia.camunda.domain.dto.ChildNode;
import com.jijia.camunda.domain.dto.ConditionInfo;
import com.jijia.camunda.domain.dto.GroupsInfo;
import com.jijia.camunda.domain.dto.Properties;
import com.jijia.camunda.domain.enums.CamundaNodeType;
import com.jijia.camunda.strategy.handler.HandlerCamundaNodeContext;
import com.jijia.camunda.strategy.service.camundaNode.CamundaNodeStrategy;
import com.jijia.camunda.strategy.service.camundaNode.abstractImpl.AbstractCamundaTypeStrategy;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.ExclusiveGatewayBuilder;
import org.camunda.bpm.model.bpmn.instance.ConditionExpression;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author leitianyu
 */
@Component
@CamundaNodeTypeAnnotation(setNodeType = CamundaNodeType.CONDITIONS)
public class ConditionsImpl extends AbstractCamundaTypeStrategy {


    @Override
    public String connect(AbstractFlowNodeBuilder<?, ?> builder,
                          String eventId,
                          ChildNode flowNode,
                          List<SequenceFlow> sequenceFlows,
                          Map<String, ChildNode> childNodeMap) throws InvocationTargetException, IllegalAccessException {

        // 添加节点进入列表
        childNodeMap.put(flowNode.getId(), flowNode);
        String name = flowNode.getName();
        String exclusiveGatewayId = flowNode.getId();

        // 创建排他网关
        ExclusiveGatewayBuilder exclusiveGatewayBuilder = builder.exclusiveGateway(exclusiveGatewayId).name(name);
        AbstractFlowNodeBuilder<?, ?> fromBuilder = moveToNode(builder, eventId);
        AbstractFlowNodeBuilder<?, ?> toBuilder = moveToNode(builder, exclusiveGatewayId);
        connect(fromBuilder, toBuilder, eventId, exclusiveGatewayId, sequenceFlows, childNodeMap);

        // 排他网关的后置节点是否存在
        if (Objects.isNull(flowNode.getBranchs()) && Objects.isNull(flowNode.getChildren())) {
            return exclusiveGatewayId;
        }

        // 排他网关的后置条件节点列表
        List<ChildNode> flowNodes = flowNode.getBranchs();
        List<String> incoming = Lists.newArrayListWithCapacity(flowNodes.size());
        List<JSONObject> conditions = Lists.newCopyOnWriteArrayList();

        // 遍历后置节点
        for (ChildNode element : flowNodes) {
            Boolean typeElse = element.getTypeElse();
            childNodeMap.put(element.getId(), element);
            ChildNode childNode = element.getChildren();

            String nodeName = element.getName();
            Properties props = element.getProps();
            String expression = props.getExpression();

            // 是否有子节点
            if (Objects.isNull(childNode) || StringUtils.isBlank(childNode.getId())) {

                incoming.add(exclusiveGatewayId);
                JSONObject condition = new JSONObject();
                // 设置条件内容
                condition.fluentPut("nodeName", nodeName)
                        .fluentPut("expression", expression)
                        .fluentPut("groups", props.getGroups())
                        .fluentPut("groupsType", props.getGroupsType()
                        )
                        .fluentPut("elseSequenceFlowId", element.getId());
                conditions.add(condition);
                continue;
            }
            // 只生成一个任务，同时设置当前任务的条件
            JSONObject incomingObj = childNode.getIncoming();
            incomingObj.put("incoming", Collections.singletonList(exclusiveGatewayId));

            CamundaNodeStrategy instance = HandlerCamundaNodeContext.getInstance(CamundaNodeType.valueOf(childNode.getType()));
            String identifier = instance.connect(exclusiveGatewayBuilder, exclusiveGatewayId, childNode, sequenceFlows, childNodeMap);

            List<SequenceFlow> flows = sequenceFlows.stream().filter(flow -> StringUtils.equals(exclusiveGatewayId, flow.getSource().getId()))
                    .collect(Collectors.toList());
            flows.stream().forEach(
                    e -> {
                        if (StringUtils.isBlank(e.getName()) && StringUtils.isNotBlank(nodeName)) {
                            e.setName(nodeName);
                        }
                        // 设置条件表达式
                        if (Objects.isNull(e.getConditionExpression()) && StringUtils.isNotBlank(expression)) {
                            Method createInstance = getDeclaredMethod(exclusiveGatewayBuilder, "createInstance", Class.class);
                            Objects.requireNonNull(createInstance).setAccessible(true);
                            ConditionExpression conditionExpression = null;
                            try {
                                conditionExpression = (ConditionExpression) createInstance.invoke(exclusiveGatewayBuilder, ConditionExpression.class);
                            } catch (IllegalAccessException | InvocationTargetException ex) {
                                throw new RuntimeException(ex);
                            }
                            conditionExpression.setTextContent(expression);


                            e.setConditionExpression(conditionExpression);
                        }
                    }
            );
            if (Objects.nonNull(identifier)) {
                incoming.add(identifier);
            }
            // 设置默认分支
            if (Boolean.TRUE.equals(typeElse)) {
                exclusiveGatewayBuilder.defaultFlow(exclusiveGatewayBuilder.done().getModelElementById(element.getId()));
            }
        }

        // 子节点
        ChildNode childNode = flowNode.getChildren();
        if (Objects.nonNull(childNode) && StringUtils.isNotBlank(childNode.getId())) {
            String parentId = childNode.getParentId();
            ChildNode parentChildNode = childNodeMap.get(parentId);
            String type = childNode.getType();
            if (!CamundaNodeType.EMPTY.isEqual(type)) {
            } else {
                if (CamundaNodeType.CONDITIONS.isEqual(parentChildNode.getType())) {
                    String endExId = parentChildNode.getId() + "ex";
                    if (incoming.isEmpty()) {
                        CamundaNodeStrategy instance = HandlerCamundaNodeContext.getInstance(CamundaNodeType.valueOf(childNode.getType()));
                        return instance.connect(exclusiveGatewayBuilder, exclusiveGatewayId, childNode, sequenceFlows,
                                childNodeMap);
                    } else {
                        JSONObject incomingObj = childNode.getIncoming();
                        // 所有 service task 连接 end exclusive gateway
                        incomingObj.put("incoming", incoming);
                        // 1.0 先进行边连接, 暂存 nextNode
                        ChildNode nextNode = childNode.getChildren();
                        childNode.setChildren(null);
                        String identifier = endExId;
                        for (int i = 0; i < incoming.size(); i++) {
                            AbstractFlowNodeBuilder<?, ?> abstractFlowNodeBuilder = moveToNode(builder, incoming.get(0));
                            ModelElementInstance endExNode = builder.done().getModelElementById(identifier);
                            if (endExNode == null) {
                                abstractFlowNodeBuilder.exclusiveGateway(identifier);
                            }
                            AbstractFlowNodeBuilder<?, ?> toNode = moveToNode(builder, identifier);
                            connect(abstractFlowNodeBuilder, toNode, incoming.get(i), identifier, sequenceFlows, childNodeMap);
                        }

                        //  针对 gateway 空任务分支 添加条件表达式
                        if (!conditions.isEmpty()) {
                            FlowElement flowElement1 = builder.done().getModelElementById(identifier);
                            // 获取从 gateway 到目标节点 未设置条件表达式的节点
                            List<SequenceFlow> flows = sequenceFlows.stream().filter(
                                            flow -> StringUtils.equals(flowElement1.getId(), flow.getTarget().getId()))
                                    .filter(
                                            flow -> StringUtils.equals(flow.getSource().getId(), exclusiveGatewayId))
                                    .collect(Collectors.toList());
                            flows.forEach(sequenceFlow -> {
                                if (!conditions.isEmpty()) {
                                    JSONObject condition = conditions.get(0);
                                    String nodeName = condition.getString("nodeName");
                                    String expression = condition.getString("expression");

                                    if (StringUtils.isBlank(sequenceFlow.getName()) && StringUtils
                                            .isNotBlank(nodeName)) {
                                        sequenceFlow.setName(nodeName);
                                    }
                                    // 设置条件表达式
                                    if (Objects.isNull(sequenceFlow.getConditionExpression())
                                            && StringUtils.isNotBlank(expression)) {
                                        Method createInstance = getDeclaredMethod(exclusiveGatewayBuilder, "createInstance", Class.class);
                                        createInstance.setAccessible(true);
                                        ConditionExpression conditionExpression = null;
                                        try {
                                            conditionExpression = (ConditionExpression) createInstance.invoke(exclusiveGatewayBuilder, ConditionExpression.class);
                                        } catch (IllegalAccessException e) {
                                            throw new RuntimeException(e);
                                        } catch (InvocationTargetException e) {
                                            throw new RuntimeException(e);
                                        }
                                        conditionExpression.setTextContent(expression);
                                        sequenceFlow.setConditionExpression(conditionExpression);
                                    }

                                    FlowElement flowElement2 = builder.done().getModelElementById(sequenceFlow.getId());
                                    if (flowElement2 != null) {
                                        flowElement2.setId(condition.getString("elseSequenceFlowId"));
                                        exclusiveGatewayBuilder.defaultFlow(exclusiveGatewayBuilder.done().getModelElementById(flowElement2.getId()));
                                        ;
                                    }

                                    conditions.remove(0);
                                }
                            });

                        }

                        // 1.1 边连接完成后，在进行 nextNode 创建
                        return connectNextNode(builder, identifier, nextNode, sequenceFlows, childNodeMap);
                    }


                }
            }

        }
        return exclusiveGatewayId;
    }

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

        ChildNode childNode = childNodeMap.get(to);
        if(childNode!=null) {
            String parentId = childNode.getParentId();
            if(StringUtils.isNotBlank(parentId)){
                ChildNode parentNode = childNodeMap.get(parentId);
                if(parentNode!=null){
                    if(CamundaNodeType.CONDITION.isEqual(parentNode.getType()) ){
                        sequenceFlowId = parentNode.getId();
                        sequenceFlow.setName(parentNode.getName());

                        if(Boolean.FALSE.equals(parentNode.getTypeElse())){
                            //解析条件表达式
                            Properties props = parentNode.getProps();
                            String expression = props.getExpression();
                            List<GroupsInfo> groups = props.getGroups();
                            String groupsType = props.getGroupsType();
                            if(StringUtils.isNotBlank(expression)){
                                paramNode.done().newInstance(ConditionExpression.class).setTextContent("${"+expression+"}");
                            }
                            else {

                                StringBuffer conditionExpression=new StringBuffer();
                                conditionExpression.append("${ ");

                                for (int i = 0; i < groups.size(); i++) {
                                    conditionExpression.append(" ( ");
                                    GroupsInfo group = groups.get(i);
                                    List<String> cids = group.getCids();
                                    String groupType = group.getGroupType();
                                    List<ConditionInfo> conditions = group.getConditions();
                                    for (int j = 0; j < conditions.size(); j++) {
                                        conditionExpression.append(" ");
                                        ConditionInfo condition = conditions.get(j);
                                        String compare = condition.getCompare();
                                        String id = condition.getId();
//                                        String title = condition.getTitle();
                                        List<Object> value = condition.getValue();
                                        String valueType = condition.getValueType();
                                        if("String".equals(valueType)){
                                            if("=".equals(compare)){
                                                String str = StringUtils.join(value, ",");
                                                str="'"+str+"'";
                                                conditionExpression.append(" "+ CamundaWorkConstants.EXPRESSION_CLASS+"strEqualsMethod("+id+","+str+") " );
                                            }
                                            else{
                                                List<String> tempList=new ArrayList<>();
                                                for (Object o : value) {
                                                    String s = o.toString();
                                                    s="'"+s+"'";
                                                    tempList.add(s);
                                                }
                                                String str = StringUtils.join(tempList, ",");
//                                                String str = StringUtils.join(value, ",");
                                                conditionExpression.append(" "+ CamundaWorkConstants.EXPRESSION_CLASS+"strContainsMethod("+id+","+str+") " );
                                            }
                                        }
                                        else if("Number".equals(valueType)){
                                            String str = StringUtils.join(value, ",");
                                            if("=".equals(compare)){
                                                conditionExpression.append(" "+ CamundaWorkConstants.EXPRESSION_CLASS+"numberEquals("+id+","+str+") " );
                                            }
                                            else if(">".equals(compare)){
                                                conditionExpression.append(" "+ CamundaWorkConstants.EXPRESSION_CLASS+"numberGt("+id+","+str+") " );
                                            }
                                            else if(">=".equals(compare)){
                                                conditionExpression.append(" "+ CamundaWorkConstants.EXPRESSION_CLASS+"numberGtEquals("+id+","+str+") " );
                                            }
                                            else if("<".equals(compare)){
                                                conditionExpression.append(" "+ CamundaWorkConstants.EXPRESSION_CLASS+"numberLt("+id+","+str+") " );
                                            }
                                            else if("<=".equals(compare)){
                                                conditionExpression.append(" "+ CamundaWorkConstants.EXPRESSION_CLASS+"numberLtEquals("+id+","+str+") " );
                                            }
                                            else if("IN".equals(compare)){
                                                conditionExpression.append(" "+ CamundaWorkConstants.EXPRESSION_CLASS+"numberContains("+id+","+str+") " );
                                            }
                                            else if("B".equals(compare)){
                                                conditionExpression.append("  "+ CamundaWorkConstants.EXPRESSION_CLASS+"b("+id+","+str+") " );
                                            }
                                            else if("AB".equals(compare)){
                                                conditionExpression.append("  "+ CamundaWorkConstants.EXPRESSION_CLASS+"ab("+id+","+str+") " );
                                            }
                                            else if("BA".equals(compare)){
                                                conditionExpression.append("  "+ CamundaWorkConstants.EXPRESSION_CLASS+"ba("+id+","+str+") " );
                                            }
                                            else if("ABA".equals(compare)){
                                                conditionExpression.append("  "+ CamundaWorkConstants.EXPRESSION_CLASS+"aba("+id+","+str+") " );
                                            }
                                        }
                                        else if("User".equals(valueType)){
                                            List<String> userIds=new ArrayList<>();
                                            for (Object o : value) {
                                                JSONObject obj=(JSONObject)o;
                                                userIds.add(obj.getString("id"));
                                            }
                                            String str=" "+ CamundaWorkConstants.EXPRESSION_CLASS+"userStrContainsMethod(\"{0}\",\"{1}\",    execution) ";
                                            str = str.replace("{0}", id);
                                            str = str.replace("{1}", StringUtils.join(userIds, ","));
                                            conditionExpression.append(str );
                                        }
                                        else if("Dept".equals(valueType)){
                                            List<String> userIds=new ArrayList<>();
                                            for (Object o : value) {
                                                JSONObject obj=(JSONObject)o;
                                                userIds.add(obj.getString("id"));
                                            }
                                            String str=" "+ CamundaWorkConstants.EXPRESSION_CLASS+"deptStrContainsMethod(\"{0}\",\"{1}\",    execution) ";
                                            str = str.replace("{0}", id);
                                            str = str.replace("{1}", StringUtils.join(userIds, ","));
                                            conditionExpression.append(str );
                                        }
                                        else{
                                            continue;
                                        }

                                        if(conditions.size()>1 && j!=(conditions.size()-1)){
                                            if("OR".equals(groupType)){
                                                conditionExpression.append(" || ");
                                            }
                                            else {
                                                conditionExpression.append(" && ");
                                            }
                                        }

                                        if(i==(conditions.size()-1)){
                                            conditionExpression.append(" ");
                                        }
                                    }


                                    conditionExpression.append(" ) ");

                                    if(groups.size()>1 && i!=(groups.size()-1) ){
                                        if("OR".equals(groupsType)){
                                            conditionExpression.append(" || ");
                                        }
                                        else {
                                            conditionExpression.append(" && ");
                                        }
                                    }


                                }
                                conditionExpression.append("} ");
                                ConditionExpression exp = sequenceFlow.builder().done().newInstance(ConditionExpression.class);
                                exp.setTextContent(conditionExpression.toString());
                                sequenceFlow.setConditionExpression(exp);
//                                    paramNode.done().newInstance(ConditionExpression.class).setTextContent(conditionExpression.toString());
                            }
                        }
                    }
                }
            }
        }

        super.afterConnectByFrom(cmdNode, toNode, from, to, sequenceFlows, childNodeMap, sequenceFlow, paramNode, sequenceFlowId);
    }
}
