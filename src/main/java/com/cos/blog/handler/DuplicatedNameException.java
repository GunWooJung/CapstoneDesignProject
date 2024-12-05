package com.cos.blog.handler;


public class DuplicatedNameException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicatedNameException(String message) {
        super(message);
    }
}