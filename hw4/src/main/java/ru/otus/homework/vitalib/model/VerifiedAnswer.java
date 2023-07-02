package ru.otus.homework.vitalib.model;

import java.util.Objects;

public class VerifiedAnswer extends Answer {
    private final boolean isCorrect;

    public VerifiedAnswer(Question question, String answer, boolean isCorrect) {
        super(question, answer);
        this.isCorrect = isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VerifiedAnswer that = (VerifiedAnswer) o;
        return isCorrect == that.isCorrect;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isCorrect);
    }
}
