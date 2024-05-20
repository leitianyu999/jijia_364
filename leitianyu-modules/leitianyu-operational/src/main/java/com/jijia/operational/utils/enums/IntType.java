package com.jijia.operational.utils.enums;

public enum IntType {

    //0
    ZERO(0),

    //1
    FIRST(1),

    //4
    FOUR(4),

    //12
    TWELVE(12),

    //15
    FIFTEEN(15),

    //16
    SIXTEEN(16),

    //18
    EIGHTEEN(18),

    //19
    NINETEEN(19),

    //999
    LAST(999);


    private final int code;

    IntType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
