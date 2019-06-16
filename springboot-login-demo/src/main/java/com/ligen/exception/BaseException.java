package com.ligen.exception;


import com.ligen.response.CodeMsg;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -3177085408025135330L;
	private String msg;
	private int errcode;
	private String uniqueKey;
	private Throwable exception;

	public BaseException() {

	}

	public BaseException(CodeMsg codeMsg, Throwable e) {
		super(codeMsg.getMsg(), e);
		this.errcode = codeMsg.getErrcode();
		this.msg = codeMsg.getMsg();
		this.exception = e;
	}

	public BaseException(CodeMsg codeMsg) {
		this.errcode = codeMsg.getErrcode();
		this.msg = codeMsg.getMsg();
	}

	public BaseException(CodeMsg codeMsg, String uniqueKey) {
		this.errcode = codeMsg.getErrcode();
		this.msg = codeMsg.getMsg();
		this.uniqueKey = uniqueKey;
	}


	public String getUniqueKey() {
		return uniqueKey;
	}

	public int getErrcode() {
		return errcode;
	}

	public String getMsg() {
		return msg;
	}

	public Throwable getException() {
		return exception;
	}
}