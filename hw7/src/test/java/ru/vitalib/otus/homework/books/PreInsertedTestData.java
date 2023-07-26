package ru.vitalib.otus.homework.books;

import ru.vitalib.otus.homework.books.domain.Author;
import ru.vitalib.otus.homework.books.domain.Book;
import ru.vitalib.otus.homework.books.domain.Comment;
import ru.vitalib.otus.homework.books.domain.Genre;

public class PreInsertedTestData {

    public static final Author EXISTING_AUTHOR = new Author(1, "Веллер Михаил");
    public static final Genre EXISTING_GENRE = new Genre(1, "Детектив");
    public static final Book EXISTING_BOOK = new Book(1, "Хочу быть дворником", EXISTING_GENRE, EXISTING_AUTHOR);
    public static final Comment EXISTING_COMMENT = new Comment(1, "Норм", EXISTING_BOOK);
}
