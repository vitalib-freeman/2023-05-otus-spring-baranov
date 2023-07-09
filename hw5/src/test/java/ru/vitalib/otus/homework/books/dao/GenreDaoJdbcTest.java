package ru.vitalib.otus.homework.books.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.vitalib.otus.homework.books.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_GENRE;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_GENRES_COUNT;

@DisplayName("GenreDao test")
@JdbcTest
@Import(GenreDaoJdbc.class)
public
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc dao;

    @Test
    @DisplayName("Find genre by id")
    void findById() {
        assertThat(dao.findById(0)).usingRecursiveComparison()
          .isEqualTo(EXISTING_GENRE);
    }

    @Test
    @DisplayName("Get genre's count")
    void getCount() {
        assertThat(dao.count()).isEqualTo(EXISTING_GENRES_COUNT);
    }

    @Test
    @DisplayName("Delete genre")
    void delete() {
        long genreForDeletion = dao.save(new Genre("Other genre"));
        assertThat(dao.count()).isEqualTo(EXISTING_GENRES_COUNT + 1);

        dao.delete(genreForDeletion);

        assertThat(dao.count()).isEqualTo(EXISTING_GENRES_COUNT);
    }

    @Test
    @DisplayName("Update genre name")
    void update() {
        Genre updatedGenre = new Genre(EXISTING_GENRE.getId(), "Пушкин Александр");

        dao.update(EXISTING_GENRE.getId(), updatedGenre);

        assertThat(dao.findById(EXISTING_GENRE.getId()))
          .usingRecursiveComparison()
          .isEqualTo(updatedGenre);
    }

    @Test
    @DisplayName("Get all genres")
    void getAll() {
        assertThat(dao.findAll())
          .usingRecursiveComparison()
          .isEqualTo(List.of(EXISTING_GENRE));
    }

    @Test
    @DisplayName("Save genre")
    void save() {
        Genre genre = new Genre("Толстой Лев");

        long savedGenreId = dao.save(genre);

        assertThat(dao.findById(savedGenreId))
          .extracting("name")
          .isEqualTo("Толстой Лев");
    }
}