package ru.otus.homework.vitalib.service;

public interface MessageProvider {

    String getTranslation(String name, String... args);
}
