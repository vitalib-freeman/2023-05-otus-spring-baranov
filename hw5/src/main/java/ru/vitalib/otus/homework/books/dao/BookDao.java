package ru.vitalib.otus.homework.books.dao;

import ru.vitalib.otus.homework.books.domain.Book;

import java.util.List;

public interface BookDao {
    long save(Book author);

    Book findById(long id);

    void delete(long id);

    void update(long id, Book author);

    int count();

    List<Book> findAll();
}
