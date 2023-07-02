package ru.otus.homework.vitalib.service;

import ru.otus.homework.vitalib.model.Question;

import java.util.List;

public interface MessageProvider {
    String getGreetingMessage();

    String getTestWelcomeMessage(String userName);

    List<Question> getQuestions();

    String getTestPassMessage();

    String getTestFailMessage();
}
