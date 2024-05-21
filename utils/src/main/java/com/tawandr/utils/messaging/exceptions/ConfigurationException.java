package com.tawandr.utils.messaging.exceptions;

/**
 * Created By Dougie T Muringani : 3/5/2020
 *
*/

public class ConfigurationException extends SystemException{

	private static final long serialVersionUID = 1L;

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}


}
