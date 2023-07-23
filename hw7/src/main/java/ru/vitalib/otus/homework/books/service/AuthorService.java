package ru.vitalib.otus.homework.books.service;

import ru.vitalib.otus.homework.books.domain.Author;

public interface AuthorService {
    Author getAuthorByName(String authorName);
}
