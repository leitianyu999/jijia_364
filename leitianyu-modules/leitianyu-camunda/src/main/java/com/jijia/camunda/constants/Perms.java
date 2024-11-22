package com.jijia.camunda.constants;

/**
 * TODO
 *
 * @author Raoqian
 * @company ZhiDao Tech
 * @create 2024/11/15
 */
public enum Perms {

    QUERY("query", "查询"),
    ADD("add", "新增"),
    EDIT("edit", "修改"),
    DELETE("delete", "删除"),
    EXPORT("export", "导出"),
    IMPORT("import", "导入"),
    FORCE("force", "强退"),;

    private final String perms;

    private final String info;


    Perms(String perms, String info) {
        this.perms = perms;
        this.info = info;
    }

    public String getPerms() {
        return perms;
    }

    public String getInfo() {
        return info;
    }

}
