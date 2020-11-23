package com.nuaa.dataplatform.util;

public enum ResultCode {
    SUCCESS(200, "OK"),
    CREATED(201, "Created"),

    BAD_REQUEST(400, "Bad request"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not found"),

    SERVER_ERROR(500,"server error"),
    ;


    private Integer code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
