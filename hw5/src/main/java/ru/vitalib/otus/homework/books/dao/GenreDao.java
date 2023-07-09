package ru.vitalib.otus.homework.books.dao;

import ru.vitalib.otus.homework.books.domain.Genre;

import java.util.List;

public interface GenreDao {
    long save(Genre author);

    Genre findById(long id);

    void delete(long id);

    void update(long id, Genre author);

    int count();

    List<Genre> findAll();

    Genre findByName(String genreName);
}
