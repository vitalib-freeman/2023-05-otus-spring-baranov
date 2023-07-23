package ru.vitalib.otus.homework.books.service;


import ru.vitalib.otus.homework.books.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(String title, String author, String genre);

    void deleteBook(long id);

    void updateBook(long id, String title, String author, String genre);

    List<BookDto> getAllBooks();

    BookDto getBookById(long id);
}
