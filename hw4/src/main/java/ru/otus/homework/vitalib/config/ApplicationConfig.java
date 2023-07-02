package ru.otus.homework.vitalib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

@Configuration
public class ApplicationConfig {
    @Bean
    Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    @Bean
    String filePath(Locale locale, QuestionProperties questionProperties) {
        return questionProperties.getFiles().stream()
          .filter(localeFile -> localeFile.getLocale().equals(locale.toLanguageTag()))
          .map(QuestionProperties.QuestionsFilePathForLocale::getFilePath)
          .findFirst()
          .orElseGet(() -> questionProperties.getFiles().get(0).getFilePath());
    }
}
