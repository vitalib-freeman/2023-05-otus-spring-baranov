package ru.vitalib.otus.homework.books.dao;

import ru.vitalib.otus.homework.books.domain.Book;

import java.util.List;

public interface BookDao {
    Book save(Book book);

    Book findById(long id);

    void delete(long id);

    List<Book> findAll();
}
