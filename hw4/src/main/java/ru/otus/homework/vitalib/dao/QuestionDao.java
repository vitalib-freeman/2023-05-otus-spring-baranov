package ru.otus.homework.vitalib.dao;

import ru.otus.homework.vitalib.model.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getQuestions();
}
