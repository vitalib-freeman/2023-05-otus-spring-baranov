package ru.otus.homework.vitalib.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.vitalib.model.Question;

import java.util.List;
import java.util.Locale;

@Service
public class InternalizationMessageProvider implements MessageProvider {
    private final Locale locale;

    private final QuestionService questionService;

    private final MessageSource messageSource;

    public InternalizationMessageProvider(Locale locale, QuestionService questionService, MessageSource messageSource) {
        this.locale = locale;
        this.questionService = questionService;
        this.messageSource = messageSource;
    }

    @Override
    public String getGreetingMessage() {
        return messageSource.getMessage("strings.hello", null, locale);
    }

    @Override
    public String getTestWelcomeMessage(String userName) {
        return messageSource.getMessage("strings.question", new String[]{userName}, locale);
    }

    @Override
    public List<Question> getQuestions() {
        return questionService.getQuestions();
    }

    @Override
    public String getTestPassMessage() {
        return messageSource.getMessage("strings.resultPass", null, locale);
    }

    @Override
    public String getTestFailMessage() {
        return messageSource.getMessage("strings.resultFail", null, locale);
    }
}
