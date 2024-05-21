package com.tawandr.utils.messaging.exceptions;

public class ValidationException extends BusinessException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ValidationException(String message, String friendlyMessage) {
		super(message, friendlyMessage);
	}

	public ValidationException(String message, String friendlyMessage, Throwable cause) {
		super(message, friendlyMessage, cause);
	}

}
