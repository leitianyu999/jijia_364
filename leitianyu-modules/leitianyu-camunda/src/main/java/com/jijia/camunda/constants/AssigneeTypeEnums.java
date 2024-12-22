package com.jijia.camunda.constants;

/**
 * TODO
 *
 * @author leitianyu999
 * @create 2024/12/19
 */
public enum AssigneeTypeEnums {

    ASSIGN_USER("ASSIGN_USER"),
    SELF_SELECT("SELF_SELECT"),
    ROLE("ROLE"),
    SELF("SELF"),
    POST("POST"),

    // NotAssigneeTypeEnums
    TO_PASS("TO_PASS"),
    TO_REFUSE("TO_REFUSE"),
    TO_ADMIN("TO_ADMIN"),
    TO_USER("TO_USER");

    private String typeName;

    AssigneeTypeEnums(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
