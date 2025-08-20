package com.ratelimiter.token_bucket.Exception;

public class TooManyRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TooManyRequestException(String string) {
		super(string);
	}
}
