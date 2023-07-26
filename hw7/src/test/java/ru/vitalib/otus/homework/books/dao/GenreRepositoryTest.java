package ru.vitalib.otus.homework.books.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_GENRE;

@DisplayName("GenreDao test")
@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Find genre by name")
    void findByName() {
        assertThat(genreRepository.findByName("Детектив")).usingRecursiveComparison()
          .isEqualTo(EXISTING_GENRE);
    }
}
