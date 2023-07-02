package ru.otus.homework.vitalib.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.vitalib.model.VerifiedAnswer;

import java.util.List;

@Service
public class SimpleGradeService implements GradeService {
    @Override
    public boolean hasPass(List<VerifiedAnswer> verifiedAnswerList, double pct) {
        long correctAnswers = verifiedAnswerList.stream()
          .filter(VerifiedAnswer::isCorrect)
          .count();
        return ((double) correctAnswers) / verifiedAnswerList.size() >= pct;
    }
}
