package com.tawandr.utils.object.exceptions;

/**
 * Created By Dougie T Muringani : 11/5/2020
 */
public class MethodNotFoundException extends RuntimeException{
    public MethodNotFoundException(String methodName) {
        super(String.format("Method [%s] not found", methodName));
    }
}
