package com.tawandr.utils.messaging.exceptions;

public class SecurityValidationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SecurityValidationException(String message) {
		super(message);
	}

	public SecurityValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
