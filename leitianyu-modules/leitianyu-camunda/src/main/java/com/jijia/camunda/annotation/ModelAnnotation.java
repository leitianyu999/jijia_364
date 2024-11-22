package com.jijia.camunda.annotation;


import com.jijia.camunda.domain.enums.ModelType;

import java.lang.annotation.*;

/**
 *
 *
 * @author leitianyu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ModelAnnotation {


    /**
     * 模型修改策略
     */
    ModelType modelUpdateType();



}
