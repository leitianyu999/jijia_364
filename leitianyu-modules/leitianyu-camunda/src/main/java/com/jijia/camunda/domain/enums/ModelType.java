package com.jijia.camunda.domain.enums;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/20
 */
public enum ModelType {

    is_PUBLISH("已发布"),
    is_NOT_PUBLISH("未发布"),
    ;



    private String decs;


    ModelType(String decs) {
        this.decs = decs;
    }




}
