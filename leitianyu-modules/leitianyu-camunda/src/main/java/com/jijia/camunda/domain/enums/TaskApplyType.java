package com.jijia.camunda.domain.enums;

/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/11/25
 */
public enum TaskApplyType {

    AGREE("1", "同意按钮"),
    DELEGATE("2", "委派按钮"),
    REFUSE("3", "拒绝按钮"),
    REVOKE("4", "撤销按钮"),
    ASSIGN("5", "转办按钮"),

    // 未完成
    ROLLBACK("6", "回退按钮"),

    // 未完成
    ADDSIGN("7", "加签按钮"),
    DELETESIGN("8", "减签按钮"),
    COMMENT("9", "评论按钮"),
    RESOLVE("10", "委派完成按钮"),
    ;



    private String type;
    private String description;

    TaskApplyType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public static TaskApplyType getByType(String type) {
        for (TaskApplyType value : TaskApplyType.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

}
