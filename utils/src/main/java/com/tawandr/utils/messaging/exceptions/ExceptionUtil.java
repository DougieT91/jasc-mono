package com.tawandr.utils.messaging.exceptions;

import com.tawandr.utils.messaging.enums.ApplicationResponseCode;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.tawandr.utils.messaging.constants.SystemConstants.NULL_OBJECT_FRIENDLY_ERROR_MESSAGE;

public abstract class ExceptionUtil {

	private ExceptionUtil() {

	}

	public static String generateStackTrace(Exception exception) {
		final StringWriter stringWriter = new StringWriter(500);
		exception.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	public static void checkObjectNullity(Object object, Class clazz) {
		if (object == null) {
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			final String errorMessage = String.format(NULL_OBJECT_FRIENDLY_ERROR_MESSAGE,methodName, clazz);
			throw new ValidationException(ApplicationResponseCode.OPERATION_NOT_ALLOWED.getMessage(),errorMessage);
		}
	}


}
