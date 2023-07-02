package ru.otus.homework.vitalib.service;

import ru.otus.homework.vitalib.model.Answer;
import ru.otus.homework.vitalib.model.VerifiedAnswer;

import java.util.List;

public interface EvaluationService {
    List<VerifiedAnswer> evaluate(List<Answer> answers);
}
