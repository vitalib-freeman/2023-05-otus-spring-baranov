package ru.vitalib.otus.homework.books.dao;

import ru.vitalib.otus.homework.books.domain.Author;

public interface AuthorDao {
    Author findByName(String authorName);
}
