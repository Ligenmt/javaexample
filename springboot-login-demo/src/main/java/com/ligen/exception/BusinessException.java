package com.ligen.exception;


import com.ligen.response.CodeMsg;

public class BusinessException extends BaseException {

	public BusinessException(CodeMsg codeMsg) {
		super(codeMsg);
	}
	public BusinessException(CodeMsg codeMsg, String uniqueKey) {
		super(codeMsg, uniqueKey);
	}
	public BusinessException(CodeMsg codeMsg, Throwable e) {
		super(codeMsg, e);
	}
}