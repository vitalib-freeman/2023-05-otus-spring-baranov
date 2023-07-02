package ru.otus.homework.vitalib.service;

import ru.otus.homework.vitalib.model.VerifiedAnswer;

import java.util.List;

public interface GradeService {
    boolean hasPass(List<VerifiedAnswer> verifiedAnswerList, double pct);
}
