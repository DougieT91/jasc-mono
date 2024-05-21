package com.tawandr.utils.messaging.i18.api;

import java.util.Locale;

public interface MessageSourceService {
    String getMessage(String propertyName, String[] args, Locale locale);
}
