package com.jijia.operational.annotation;


import com.jijia.operational.utils.enums.DeskInsertType;

import java.lang.annotation.*;

/**
 *
 *
 * @author leitianyu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DeskInsertTypeAnnotation {


    /**
     * 台账生成策略类型
     */
    DeskInsertType deskInsertType();



}
