package com.tawandr.utils.messaging.exceptions;

/**
 * Created By Dougie T Muringani : 27/6/2019
 */
public class NullRequestException extends BusinessException {
    private final Class<?> requestType;

    public NullRequestException() {
        super("Non Null request Required but not provided");
        requestType = null;
    }

    public NullRequestException(Class<?> requestType) {
        this(requestType, String.format("Non Null %s object Required but not provided", requestType.getSimpleName()));
    }

    public NullRequestException(Class<?> requestType, String message) {
        super(message);
        this.requestType=requestType;
    }
}
