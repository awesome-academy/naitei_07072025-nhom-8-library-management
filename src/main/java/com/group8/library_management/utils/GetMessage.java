package com.group8.library_management.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetMessage {
    private final MessageSource messageSource;

    public GetMessage(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String msg(String key, Object... args) {
        return messageSource.getMessage(key, args, key, LocaleContextHolder.getLocale());
    }
}
