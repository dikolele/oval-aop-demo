package com.littlefairy.exceptions;

import lombok.Data;

@Data
public class ParamsIllegalException extends RuntimeException {

    private static final long serialVersionUID = -1483464712920296137L;

    private Integer code;

    private String mess;

    public ParamsIllegalException(Integer code, String mess) {
        super();
        this.code = code;
        this.mess = mess;
    }

    public ParamsIllegalException(String mess) {
        super(mess);
    }
}
