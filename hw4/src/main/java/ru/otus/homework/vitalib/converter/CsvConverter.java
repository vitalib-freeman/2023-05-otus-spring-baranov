package ru.otus.homework.vitalib.converter;

import org.springframework.stereotype.Component;
import ru.otus.homework.vitalib.model.Question;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CsvConverter {
    public List<Question> convert(List<String[]> allRows) {
        return allRows.stream()
          .map(line -> new Question(line[0], line[1]))
          .collect(Collectors.toList());
    }
}
