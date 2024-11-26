package com.jijia.camunda.annotation;


import com.jijia.camunda.domain.enums.TaskApplyType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TaskAnnotation {

    /**
     * 模型修改策略
     */
    TaskApplyType[] TaskCompleteType();

}
