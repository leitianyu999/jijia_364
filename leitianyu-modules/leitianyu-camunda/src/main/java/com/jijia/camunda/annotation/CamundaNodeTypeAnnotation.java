package com.jijia.camunda.annotation;


import com.jijia.camunda.domain.enums.CamundaNodeType;

import java.lang.annotation.*;

/**
 *
 *
 * @author leitianyu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CamundaNodeTypeAnnotation {


    /**
     * 模型修改策略
     */
    CamundaNodeType[] setNodeType();



}
