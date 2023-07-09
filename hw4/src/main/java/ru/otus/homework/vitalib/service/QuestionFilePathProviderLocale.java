package ru.otus.homework.vitalib.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.homework.vitalib.config.QuestionFilesPathsHolder;
import ru.otus.homework.vitalib.config.QuestionProperties;

@Service
@RequiredArgsConstructor
public class QuestionFilePathProviderLocale implements QuestionFilePathProvider {


    private final QuestionFilesPathsHolder questionFilesPathsHolder;

    @Override
    public String getPath() {
        return questionFilesPathsHolder.getFilesPaths().stream()
          .filter(localeFile -> localeFile.getLocale().equals(LocaleContextHolder.getLocale().toLanguageTag()))
          .map(QuestionProperties.QuestionsFilePathForLocale::getFilePath)
          .findFirst()
          .orElseGet(() -> questionFilesPathsHolder.getFilesPaths().get(0).getFilePath());
    }
}
