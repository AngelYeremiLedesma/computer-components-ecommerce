package com.aledesma.app.exceptions;

public class EmailAlreadyConfirmedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailAlreadyConfirmedException(String message) {
		super(message);
	}
}
