package com.jijia.operational.annotation;


import com.jijia.operational.utils.enums.ProgramType;

import java.lang.annotation.*;

/**
 *
 *
 * @author leitianyu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ProgramInsertTypeAnnotation {


    /**
     * 项目类型生成策略类型
     */
    ProgramType programType();



}
