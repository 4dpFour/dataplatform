package com.nuaa.dataplatform.util;

public enum ResultCode {
    /** 添加新的状态码只要往这儿添加即可 */
    SUCCESS(200, "OK"),

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
