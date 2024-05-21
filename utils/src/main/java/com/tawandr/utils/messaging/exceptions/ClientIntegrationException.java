package com.tawandr.utils.messaging.exceptions;

public class ClientIntegrationException extends RuntimeException {

    public ClientIntegrationException(String message) {
        super("Error occurred while integrating to external web-service:: " + message);
    }

    public ClientIntegrationException(String message,
                                      Throwable cause) {
        super("Error occurred while integrating to external web-service:: " + message, cause);
    }
}
