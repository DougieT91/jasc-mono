package com.tawandr.utils.messaging.builders;

import org.apache.commons.lang3.StringUtils;

public class UtilityBuilder {

    private UtilityBuilder() {
        super();
    }

    public static void buildErrorMessage(final StringBuilder builder, final boolean isMissingValue, final String errorMessage) {
        if (isMissingValue) {
            if (builder.length() == 0) {
                builder.append(errorMessage);
            } else {
                builder.append(", ").append(errorMessage);
            }
        }
    }

    public static void buildSpacedMessage(final StringBuilder builder, final String messagePart) {
        final boolean isAvailable = StringUtils.isNotEmpty(messagePart);
        if (isAvailable) {
            if (builder.length() == 0) {
                builder.append(messagePart);
            } else {
                builder.append(" ").append(messagePart);
            }
        }
    }
}