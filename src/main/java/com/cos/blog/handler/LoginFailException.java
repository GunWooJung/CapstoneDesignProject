package com.cos.blog.handler;


public class LoginFailException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginFailException(String message) {
        super(message);
    }
}