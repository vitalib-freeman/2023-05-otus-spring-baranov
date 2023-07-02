package ru.otus.homework.vitalib.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.vitalib.model.Answer;
import ru.otus.homework.vitalib.model.VerifiedAnswer;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleEvaluationService implements EvaluationService {
    @Override
    public List<VerifiedAnswer> evaluate(List<Answer> answers) {
        return answers.stream()
          .map(
            ans -> new VerifiedAnswer(
              ans.getQuestion(), ans.getAnswer(), ans.getQuestion().getAnswerText().equals(ans.getAnswer()))
          )
          .collect(Collectors.toList());
    }
}
