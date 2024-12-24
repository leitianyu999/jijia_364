package com.jijia.camunda.strategy.service.camundaNode.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jijia.camunda.annotation.CamundaNodeTypeAnnotation;
import com.jijia.camunda.domain.dto.ChildNode;
import com.jijia.camunda.domain.enums.CamundaNodeType;
import com.jijia.camunda.strategy.handler.HandlerCamundaNodeContext;
import com.jijia.camunda.strategy.service.camundaNode.CamundaNodeStrategy;
import com.jijia.camunda.strategy.service.camundaNode.abstractImpl.AbstractCamundaTypeStrategy;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.ParallelGatewayBuilder;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author leitianyu
 */
@Component
@CamundaNodeTypeAnnotation(setNodeType = CamundaNodeType.CONCURRENTS)
public class ConcurrentsImpl extends AbstractCamundaTypeStrategy {


    @Override
    public String connect(AbstractFlowNodeBuilder<?, ?> builder,
                          String eventId,
                          ChildNode flowNode,
                          List<SequenceFlow> sequenceFlows,
                          Map<String, ChildNode> childNodeMap) throws InvocationTargetException, IllegalAccessException {

        // 
        childNodeMap.put(flowNode.getId(), flowNode);
        String name = flowNode.getName();
        String parallelGatewayId = flowNode.getId();

        // 并行网关
        ParallelGatewayBuilder parallelGatewayBuilder = builder.parallelGateway(parallelGatewayId).name(name);
        // 并行网关的前置节点
        // 暂时封存
//        AbstractFlowNodeBuilder<?, ?> fromBuilder = moveToNode(builder, eventId);

        // 连线
        connect(builder, parallelGatewayBuilder, eventId, parallelGatewayId, sequenceFlows, childNodeMap);

        // 并行网关的后置节点是否存在
        if (Objects.isNull(flowNode.getBranchs()) && Objects.isNull(flowNode.getChildren())) {
            return parallelGatewayId;
        }

        // 并行网关的后置条件节点列表
        List<ChildNode> flowNodes = flowNode.getBranchs();
        List<String> incoming = Lists.newArrayListWithCapacity(flowNodes.size());

        // 遍历后置节点

        for (ChildNode element : flowNodes) {
            childNodeMap.put(element.getId(), element);
            ChildNode childNode = element.getChildren();

            // 后置节点的后置节点是否存在
            if (Objects.isNull(childNode) || StringUtils.isBlank(childNode.getId())) {
                incoming.add(parallelGatewayId);
                continue;
            }

            // 嵌套循环
            CamundaNodeStrategy instance = HandlerCamundaNodeContext.getInstance(CamundaNodeType.valueOf(childNode.getType()));
            String identifier = instance.connect(parallelGatewayBuilder, parallelGatewayId, childNode, sequenceFlows, childNodeMap);
            if (Objects.nonNull(identifier)) {
                incoming.add(identifier);
            }
        }

        // 并行网关的后置节点是否存在
        ChildNode childNode = flowNode.getChildren();
        if (Objects.nonNull(childNode) && StringUtils.isNotBlank(childNode.getId())) {
            String parentId = childNode.getParentId();
            ChildNode parentChildNode = childNodeMap.get(parentId);
            String type = childNode.getType();
            if (!CamundaNodeType.EMPTY.isEqual(type)) {
                // 未知作用
            } else {
                if (CamundaNodeType.CONCURRENTS.isEqual(parentChildNode.getType())) {
                    String endExId = parentChildNode.getId() + "ex";

                    // 普通结束网关
                    if (CollectionUtils.isEmpty(incoming)) {
                        // 此并行分支无节点列表
                        CamundaNodeStrategy instance = HandlerCamundaNodeContext.getInstance(CamundaNodeType.valueOf(childNode.getType()));
                        return instance.connect(parallelGatewayBuilder, parallelGatewayId, childNode, sequenceFlows, childNodeMap);
                    } else {
                        JSONObject incomingObj = childNode.getIncoming();
                        // 所有 service task 连接 end parallel gateway
                        incomingObj.put("incoming", incoming);
                        // 1.0 先进行边连接, 暂存 nextNode
                        ChildNode nextNode = childNode.getChildren();
                        childNode.setChildren(null);
                        String identifier = endExId;
                        for (int i = 0; i < incoming.size(); i++) {
                            AbstractFlowNodeBuilder<?, ?> abstractFlowNodeBuilder = moveToNode(builder, incoming.get(0));
                            ModelElementInstance endExNode = builder.done().getModelElementById(identifier);
                            if (endExNode == null) {
                                abstractFlowNodeBuilder.parallelGateway(identifier);
                            }
                            AbstractFlowNodeBuilder<?, ?> toNode = moveToNode(builder, identifier);
                            connect(abstractFlowNodeBuilder, toNode, incoming.get(i), identifier, sequenceFlows, childNodeMap);
                        }
                        // 1.1 边连接完成后，在进行 nextNode 创建
                        childNode.setChildren(nextNode);
                        return connectNextNode(builder, identifier, nextNode, sequenceFlows, childNodeMap);
                    }
                }
            }

        }
        return parallelGatewayId;
    }
}
