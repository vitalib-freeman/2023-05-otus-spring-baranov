package ru.otus.homework.vitalib.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "questions")
public class QuestionProperties {
    private List<QuestionsFilePathForLocale> files;

    private double passRate;

    public double getPassRate() {
        return passRate;
    }

    public void setPassRate(double passRate) {
        this.passRate = passRate;
    }

    public List<QuestionsFilePathForLocale> getFiles() {
        return files;
    }

    public void setFiles(List<QuestionsFilePathForLocale> files) {
        this.files = files;
    }

    public static class QuestionsFilePathForLocale {
        private String filePath;

        private String locale;

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }
    }
}
