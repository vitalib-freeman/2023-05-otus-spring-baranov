package ru.vitalib.otus.homework.books;

import ru.vitalib.otus.homework.books.domain.Author;
import ru.vitalib.otus.homework.books.domain.Book;
import ru.vitalib.otus.homework.books.domain.Genre;

public class PreInsertedTestData {

    public static final Author EXISTING_AUTHOR = new Author(0, "Веллер Михаил");
    public static final int EXISTING_AUTHORS_COUNT = 1;
    public static final Genre EXISTING_GENRE = new Genre(0, "Детектив");
    public static final int EXISTING_GENRES_COUNT = 1;
    public static final Book EXISTING_BOOK = new Book(0, "Хочу быть дворником", EXISTING_GENRE, EXISTING_AUTHOR);
    public static final int EXISTING_BOOKS_COUNT = 1;
}
