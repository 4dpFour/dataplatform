package com.nuaa.dataplatform.util;

import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;

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
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public Result(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
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

    /** 成功，不附带数据 */
    public static Result success() {
        return new Result(ResultCode.SUCCESS);
    }

    /** 成功，附带数据 */
    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    /** 失败，使用默认的 msg */
    public static Result failure(ResultCode resultCode) {
        return new Result(resultCode);
    }

    /** 失败，使用自定义的 msg */
    public static Result failure(ResultCode resultCode, String msg) {
        Result result = new Result(resultCode);
        result.setMsg(msg);
        return result;
    }

}
