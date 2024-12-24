package com.jijia.camunda.domain.enums;

import com.google.common.collect.Maps;
import com.jijia.camunda.constants.CamundaWorkConstants;
import org.camunda.bpm.model.bpmn.instance.*;

import java.util.Arrays;
import java.util.Map;

/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/11/21
 */
public enum CamundaNodeType {

    /**
     * 并行事件
     */
    CONCURRENTS("CONCURRENTS", ParallelGateway.class),
    CONCURRENT("CONCURRENT", SequenceFlow.class),
    /**
     * 排他事件
     */
    CONDITION("CONDITION", Condition.class),
    CONDITIONS("CONDITIONS", ExclusiveGateway.class),
    /*
     * 包容事件
     */
    INCLUSIVE("INCLUSIVE", SequenceFlow.class),
    INCLUSIVES("INCLUSIVES", InclusiveGateway.class),
    /**
     * 任务
     */
    APPROVAL("APPROVAL", UserTask.class),
    TASK("TASK", UserTask.class),
    ROOT("ROOT", UserTask.class),
    CC("CC", ServiceTask.class),
    TRIGGER("TRIGGER", ServiceTask.class),
    DELAY("DELAY", IntermediateCatchEvent.class),
    START_EVENT(CamundaWorkConstants.START_EVENT_ID, StartEvent.class),
    END_EVENT(CamundaWorkConstants.END_EVENT_ID, EndEvent.class),
    EMPTY("EMPTY", Object.class),;
    private String type;

    private Class<?> typeClass;

    CamundaNodeType(String type, Class<?> typeClass) {
        this.type = type;
        this.typeClass = typeClass;
    }



    public static CamundaNodeType getByTypeClass(Class<?> typeClass) {
        for (CamundaNodeType element : CamundaNodeType.values()) {
            // 判断是否继承
            if (typeClass.isInstance(element.typeClass) || Arrays.stream(typeClass.getInterfaces()).anyMatch(aClass -> aClass.equals(element.typeClass))) {
                return element;
            }
        }
        return null;
    }

    public final static Map<String, Class<?>> TYPE_MAP = Maps.newHashMap();

    static {
        for (CamundaNodeType element : CamundaNodeType.values()) {
            TYPE_MAP.put(element.type, element.typeClass);
        }
    }

    public boolean isEqual(String type) {
        return this.type.equals(type);
    }

}
