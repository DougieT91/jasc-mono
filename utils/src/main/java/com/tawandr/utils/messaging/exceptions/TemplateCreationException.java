package com.tawandr.utils.messaging.exceptions;

/**
 * Created By Dougie T Muringani : 29/9/2019
 */
public class TemplateCreationException extends RuntimeException {
    public TemplateCreationException() {
        super();
    }

    public TemplateCreationException(String message) {
        super(message);
    }

    public TemplateCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
