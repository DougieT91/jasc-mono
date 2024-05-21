package com.tawandr.utils.messaging.builders;

public class ErrorBuilder {

    private ErrorBuilder() {
        super();
    }

    public static void buildErrorMessage(final StringBuilder builder, final boolean isMissingValue, final String errorMessage) {
        UtilityBuilder.buildErrorMessage(builder, isMissingValue, errorMessage);
    }
}
