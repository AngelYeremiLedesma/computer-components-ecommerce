package com.aledesma.app.exceptions;

public class ConfirmationTokenNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConfirmationTokenNotFoundException(String message) {
		super(message);
	}
}
