package com.cos.blog.handler;


public class DuplicatedIdException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicatedIdException(String message) {
        super(message);
    }
}