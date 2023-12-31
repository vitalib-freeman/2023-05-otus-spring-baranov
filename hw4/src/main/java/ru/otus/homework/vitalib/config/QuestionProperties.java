package ru.otus.homework.vitalib.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "questions")
public class QuestionProperties implements QuestionFilesPathsHolder {
    private List<QuestionsFilePathForLocale> files;

    private double passRate;

    public double getPassRate() {
        return passRate;
    }

    public void setPassRate(double passRate) {
        this.passRate = passRate;
    }

    public List<QuestionsFilePathForLocale> getFilesPaths() {
        return files;
    }

    public void setFiles(List<QuestionsFilePathForLocale> files) {
        this.files = files;
    }
}
