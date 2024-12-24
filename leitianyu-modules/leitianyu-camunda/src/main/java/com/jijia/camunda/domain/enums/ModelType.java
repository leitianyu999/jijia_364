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
    is_STOP("已停用"),
    ;




    private String decs;


    ModelType(String decs) {
        this.decs = decs;
    }


    public static ModelType getEnum(String value) {
        switch (value) {
            case "1":
                return is_PUBLISH;
            case "0":
                return is_NOT_PUBLISH;
            case "2":
                return is_STOP;
        }
        return null;
    }


}
