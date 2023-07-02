package ru.otus.homework.vitalib.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.homework.vitalib.config.QuestionProperties;
import ru.otus.homework.vitalib.model.Answer;
import ru.otus.homework.vitalib.model.Question;
import ru.otus.homework.vitalib.model.VerifiedAnswer;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CliRunner.class})
class RunnerTest {
  @Autowired
  private CliRunner cliRunner;
  @MockBean
  private CliWriter writer;
  @MockBean
  private CliReader reader;
  @MockBean
  private EvaluationService evaluationService;
  @MockBean
  private GradeService gradeService;
  @MockBean
  private QuestionProperties questionProperties;
  @MockBean
  private MessageProvider messageProvider;

  @Test
  public void testSuccessTest() {
    Question question = new Question("QuestionText", "QuestionAnswer");
    List<Question> questions = List.of(question);
    mockMessageSource(questions);
    Answer answer = new Answer(question, "QuestionAnswer");
    List<Answer> answers = List.of(answer);
    VerifiedAnswer verifiedAnswer = new VerifiedAnswer(question, "QuestionAnswer", true);
    when(reader.read()).thenReturn("QuestionAnswer");
    when(evaluationService.evaluate(answers)).thenReturn(List.of(verifiedAnswer));
    when(gradeService.hasPass(List.of(verifiedAnswer), 0d)).thenReturn(true);

    cliRunner.run();

    verify(writer).write("Hi, pls enter your name:");
    verify(writer).write("---> QuestionText: ");
    verify(reader, Mockito.times(2)).read();
    verify(evaluationService).evaluate(answers);
    verify(gradeService).hasPass(List.of(verifiedAnswer), 0);
    verify(writer).write("Congratulations, you have passed the test\n");
  }

  private void mockMessageSource(List<Question> questions) {
    when(messageProvider.getGreetingMessage()).thenReturn("Hi, pls enter your name:");
    when(messageProvider.getTestPassMessage()).thenReturn("Congratulations, you have passed the test");
    when(messageProvider.getQuestions()).thenReturn(questions);
  }
}
