package ru.otus.homework.vitalib.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.vitalib.config.QuestionProperties;
import ru.otus.homework.vitalib.model.Answer;
import ru.otus.homework.vitalib.model.Question;
import ru.otus.homework.vitalib.model.VerifiedAnswer;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CliRunner {
    private final Writer writer;

    private final Reader reader;

    private final EvaluationService evaluationService;

    private final GradeService gradeService;

    private final QuestionProperties questionProperties;

    private final MessageProvider messageProvider;

    public void run() {
        String userName = getUserName();
        List<Answer> answers = getUserAnswers(userName);
        boolean hasPass = getGrade(answers);
        printResult(hasPass);
    }

    public void printResult(boolean hasPass) {
        String result = hasPass
          ? messageProvider.getTestPassMessage()
          : messageProvider.getTestFailMessage();
        writer.write(String.format("%s\n", result));
    }

    public boolean getGrade(List<Answer> answers) {
        List<VerifiedAnswer> verifiedAnswers = evaluationService.evaluate(answers);
        return gradeService.hasPass(verifiedAnswers, questionProperties.getPassRate());
    }

    public List<Answer> getUserAnswers(String userName) {
        writer.write(String.format("%s\n", messageProvider.getTestWelcomeMessage(userName)));
        List<Question> questions = messageProvider.getQuestions();
        List<Answer> answers = getAnswers(questions);
        return answers;
    }

    public String getUserName() {
        writer.write(messageProvider.getGreetingMessage());
        return reader.read();
    }

    private List<Answer> getAnswers(List<Question> questions) {
        List<Answer> answers = new ArrayList<>();
        for (Question question : questions) {
            writer.write(String.format("---> %s: ", question.getQuestionText()));
            String answer = reader.read();
            answers.add(new Answer(question, answer));
        }
        return answers;
    }

}
