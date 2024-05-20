package com.jijia.operational.utils.enums;

import com.jijia.operational.utils.constants.DeskConstants;

/**
 * 插号枚举类
 *
 * @author leitianyu
 */
public enum DeskInsertType {

    //建设策略
    JIAN_SHE(1, DeskConstants.Desk_JIANSHE),
    //23水利策略
    SHUI_LI_23(2,DeskConstants.Desk_SHUILI_23),
    //水利报建策略
    BAO_JIAN(3,DeskConstants.Desk_BAOJIAN),
    //24水利策略
    SHUI_LI_24(4,DeskConstants.Desk_SHUILI_24);




    private Integer status;
    private String msg;


    DeskInsertType(Integer status,String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static DeskInsertType getEnum(Integer status) {
        DeskInsertType[] deskInsertTypes = DeskInsertType.values();
        for (DeskInsertType deskInsertType : deskInsertTypes) {
            if (status.equals(deskInsertType.status)) {
                return deskInsertType;
            }
        }
        return null;
    }

    public static DeskInsertType getEnum(String msg) {
        DeskInsertType[] deskInsertTypes = DeskInsertType.values();
        for (DeskInsertType deskInsertType : deskInsertTypes) {
            if (msg.equals(deskInsertType.msg)) {
                return deskInsertType;
            }
        }
        return null;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "DeskInsertType{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
