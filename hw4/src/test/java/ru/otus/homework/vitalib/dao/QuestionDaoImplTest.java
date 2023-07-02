package ru.otus.homework.vitalib.dao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.vitalib.converter.CsvConverter;
import ru.otus.homework.vitalib.utils.CsvParser;
import ru.otus.homework.vitalib.utils.FileReaderProvider;
import ru.otus.homework.vitalib.model.Question;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionDaoImplTest {

  @Mock
  private FileReaderProvider fileDataReader;
  @Mock
  private CsvParser csvParser;
  @Mock
  private CsvConverter csvConverter;

  @InjectMocks
  private QuestionDaoCsv questionDaoCsv;

  @Test
  public void getQuestionsFromFile() {
    String[] row = {"one", "two"};
    List<String[]> rows = new ArrayList<>();
    rows.add(row);
    when(csvParser.getLines(any())).thenReturn(rows);
    when(csvConverter.convert(any())).thenReturn(List.of(new Question("one", "two")));

    List<Question> questions = questionDaoCsv.getQuestions();

    assertNotNull(questions);
    verify(fileDataReader).getDataReader(any());
    verify(csvParser).getLines(any());
    verify(csvConverter).convert(any());
    assertEquals(List.of(new Question("one", "two")), questions);
  }
}
