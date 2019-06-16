package com.ligen.exception;


import com.ligen.response.CodeMsg;

public class AuthenticationException extends BaseException {
	public AuthenticationException(CodeMsg codeMsg, Throwable e) {
		super(codeMsg, e);
	}
	public AuthenticationException(CodeMsg codeMsg) {
		super(codeMsg);
	}

	public AuthenticationException() {
	}
	public AuthenticationException(CodeMsg codeMsg, String uniqueKey) {
		super(codeMsg, uniqueKey);
	}

}
