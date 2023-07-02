package ru.otus.homework.vitalib.model;

import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class Question {
    private final String questionText;

    private final String answerText;

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    @Override
    public String toString() {
        return "Question: " + questionText + "\n\tAnswer:" + answerText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question = (Question) o;
        return Objects.equals(questionText, question.questionText) && Objects.equals(answerText, question.answerText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionText, answerText);
    }
}
