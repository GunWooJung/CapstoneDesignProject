package com.cos.blog.handler;


public class NoDataFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoDataFoundException(String message) {
        super(message);
    }
}