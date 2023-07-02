package ru.otus.homework.vitalib.exception;

public class CsvFileException extends RuntimeException {
    public CsvFileException(Exception e) {
        super(e);
    }
}
