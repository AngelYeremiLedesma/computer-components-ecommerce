package com.aledesma.app.exceptions;

public class ConfirmationTokenExpiredException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfirmationTokenExpiredException(String message) {
		super(message);
	}
}
