package com.tawandr.utils.object.exceptions;

/**
 * Created By Dougie T Muringani : 11/5/2020
 */
public class TypeNotFoundException extends RuntimeException{
    public TypeNotFoundException(String typeName) {
        super(String.format("Type [%s] not found", typeName));
    }

    public TypeNotFoundException() {
        super();
    }

    public TypeNotFoundException(String message,
                                 Throwable cause) {
        super(message, cause);
    }
}
