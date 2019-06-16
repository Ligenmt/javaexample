package com.ligen.response;

/**
 * Created by ligen on 2018/4/11.
 */
public class CodeMsg {

    private int errcode;
    private String msg;

    private CodeMsg(int errcode,String msg ) {
        this.errcode = errcode;
        this.msg = msg;
    }

    public static CodeMsg 请求成功 = new CodeMsg(0, "请求成功");
    public static CodeMsg 参数不正确 = new CodeMsg(400, "参数不正确");
    public static CodeMsg 无权限 = new CodeMsg(403, "参数不正确");
    public static CodeMsg 需要登录 = new CodeMsg(401, "需要登录");
    public static CodeMsg 内部错误 = new CodeMsg(500, "内部错误");

    public int getErrcode() {
        return errcode;
    }

    public String getMsg() {
        return msg;
    }

}
