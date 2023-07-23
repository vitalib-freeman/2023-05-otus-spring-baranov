package ru.vitalib.otus.homework.books.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_GENRE;

@DisplayName("GenreDao test")
@DataJpaTest
@Import(GenreDaoJPA.class)
class GenreDaoJPATest {

    @Autowired
    private GenreDaoJPA genreRepositoryJPA;

    @Test
    @DisplayName("Find genre by name")
    void findByName() {
        assertThat(genreRepositoryJPA.findByName("Детектив")).usingRecursiveComparison()
          .isEqualTo(EXISTING_GENRE);
    }
}
