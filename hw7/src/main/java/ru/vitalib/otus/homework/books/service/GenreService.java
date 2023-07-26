package ru.vitalib.otus.homework.books.service;

import ru.vitalib.otus.homework.books.domain.Genre;

public interface GenreService {
    Genre getGenreByName(String genreName);
}
