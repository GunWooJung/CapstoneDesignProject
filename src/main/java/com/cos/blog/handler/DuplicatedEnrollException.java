package com.cos.blog.handler;


public class DuplicatedEnrollException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicatedEnrollException(String message) {
        super(message);
    }
}