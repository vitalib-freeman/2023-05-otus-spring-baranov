package ru.otus.homework.vitalib.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework.vitalib.model.Answer;
import ru.otus.homework.vitalib.model.Question;
import ru.otus.homework.vitalib.model.VerifiedAnswer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SimpleEvaluationService.class)
class SimpleEvaluationServiceTest {

    @Autowired
    private EvaluationService evaluationService;

    @Test
    public void testSuccessEvaluation() {
        List<Answer> answers = List.of(new Answer(new Question("question", "answer"), "answer"));

        List<VerifiedAnswer> verifiedAnswers = evaluationService.evaluate(answers);

        assertEquals(1, verifiedAnswers.size());
        assertTrue(verifiedAnswers.iterator().next().isCorrect());
    }

    @Test
    public void testFailedEvaluation() {
        List<Answer> answers = List.of(new Answer(new Question("question", "answer"), "wrong"));

        List<VerifiedAnswer> verifiedAnswers = evaluationService.evaluate(answers);

        assertEquals(1, verifiedAnswers.size());
        assertFalse(verifiedAnswers.iterator().next().isCorrect());

    }
}
