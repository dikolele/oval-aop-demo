package com.littlefairy.exceptions;

public enum CommonCodeEnum {
    PARAM_ERROR(400);

    private Integer code;
    CommonCodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
