package com.nuaa.dataplatform.util;

import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable {
    private int code;    //默认200
    private String msg;    //消息
    private Object data;   //存放的数据，可为空

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result() {
        this.code = 200;
        this.msg = switchMsg(200);
    }

    /** 提供 code，自动选择 msg */
    public Result(int code) {
        this.code = code;
        this.msg = switchMsg(code);
    }

    /** 提供 code，强制指定 msg */
    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /** 提供 code 和 data ，自动选择 msg */
    public Result(int code, Object data) {
        this.code = code;
        this.msg = switchMsg(code);
        this.data = data;
    }

    /** 提供 code 和 data ，强制指定 msg */
    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /** 转化为Json字符串 */
    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("code", code);
        if (msg != null && msg.length() != 0) {
            json.put("msg", msg);
        }
        if (data != null) {
            json.put("data", data);
        }
        return json.toJSONString();
    }

    public static Result success() {
        return new Result(200);
    }

    public static Result success(Object data) {
        return new Result(200, data);
    }

    public static Result failure(int code) {
        return new Result(code);
    }

    public static Result failure(int code, String msg) {
        return new Result(code, msg);
    }

    /** 添加状态码对应信息从这里添加即可 */
    private String switchMsg(int code) {
        switch (code) {
            case 200:
                return "OK";
            case 201:
                return "created";
            case 400:
                return "Bad request";
            case 403:
                return "Forbidden";
            case 404:
                return "Not found";
        }
        return "error";
    }
}
