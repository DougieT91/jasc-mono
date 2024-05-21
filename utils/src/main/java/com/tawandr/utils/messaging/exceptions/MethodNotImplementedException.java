package com.tawandr.utils.messaging.exceptions;

public class MethodNotImplementedException extends RuntimeException {

    public MethodNotImplementedException() {
        super("Method is Not Implemented");
    }
    public MethodNotImplementedException(String message) {
        super(String.format("Method is Not Implemented. [Reason: %s]", message));
    }
}
