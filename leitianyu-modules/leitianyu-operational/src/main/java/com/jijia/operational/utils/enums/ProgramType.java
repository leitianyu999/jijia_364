package com.jijia.operational.utils.enums;

import com.jijia.operational.utils.constants.DeskConstants;

/**
 * @author leitianyu
 */
public enum ProgramType {
    //原材策略
    YUAN_CAI(1, DeskConstants.Desk_YUANCAI),
    //现场策略
    XIAN_CHANG(2, DeskConstants.DESK_XIANCHANG);

    private Integer status;
    private String msg;

    ProgramType(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static ProgramType getEnum(Integer status) {
        ProgramType[] programTypes = ProgramType.values();
        for (ProgramType programType : programTypes) {
            if (status.equals(programType.status)) {
                return programType;
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
        return "ProgramType{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
