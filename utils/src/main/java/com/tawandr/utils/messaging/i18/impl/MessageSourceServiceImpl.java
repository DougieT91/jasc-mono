package com.tawandr.utils.messaging.i18.impl;

import com.tawandr.utils.messaging.i18.api.MessageSourceService;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class MessageSourceServiceImpl implements MessageSourceService {
    private final MessageSource messageSource;

    public MessageSourceServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(final String propertyName, final String[] args, Locale locale) {
        return messageSource.getMessage(propertyName, args, locale);
    }
}
