package ru.vitalib.otus.homework.books.dao;

import ru.vitalib.otus.homework.books.domain.Author;

import java.util.List;

public interface AuthorDao {
    long save(Author author);

    Author findById(long id);

    void delete(long id);

    void update(long id, Author author);

    int count();

    List<Author> findAll();

    Author findByName(String authorName);
}
