package ru.vitalib.otus.homework.books.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitalib.otus.homework.books.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(String genreName);
}
