package com.ligen.response;

import java.io.Serializable;

public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    public static int OK = 0;
    private T data;

    protected int errcode;
    protected String msg = "请求成功";;

    public Response() {
        this.errcode = 0;
        this.msg = "请求成功";
    }

    public Response(int errcode, String msg) {
        this();
        this.errcode = errcode;
        this.msg = msg;
    }

    public Response(int errcode, String msg, T data) {
        this();
        this.errcode = errcode;
        this.msg = msg;
        this.data = data;
    }

    public Response(T data) {
        this();
        this.data = data;
    }


    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
