package ru.otus.homework.vitalib.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class InternalizationMessageProvider implements MessageProvider {
    //TODO get from properties
    private final Locale locale;

    private final MessageSource messageSource;


    public InternalizationMessageProvider(MessageSource messageSource) {
        this.locale = LocaleContextHolder.getLocale();
        this.messageSource = messageSource;
    }

    @Override
    public String getTranslation(String name, String... args) {
        return messageSource.getMessage(name, args, locale);
    }

}
