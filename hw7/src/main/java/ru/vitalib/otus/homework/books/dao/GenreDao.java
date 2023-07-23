package ru.vitalib.otus.homework.books.dao;

import ru.vitalib.otus.homework.books.domain.Genre;

public interface GenreDao {
    Genre findByName(String genreName);
}
