package com.tawandr.utils.messaging.exceptions;

import java.util.Optional;

/**
 * Created By Dougie T Muringani : 29/9/2019
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final transient Optional<String> friendlyMessage;

    public BusinessException(String message, String friendlyMessage) {
        super(message);
        this.friendlyMessage = Optional.of(friendlyMessage);
    }

    public BusinessException(String message, String friendlyMessage, Throwable cause) {
        super(message, cause);
        this.friendlyMessage = Optional.of(friendlyMessage);
    }

    public BusinessException(String message) {
        super(message);
        this.friendlyMessage = Optional.<String>empty();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.friendlyMessage = Optional.<String>empty();
    }

    public Optional<String> getFriendlyMessage() {
        return friendlyMessage;
    }
}
