package ru.vitalib.otus.homework.books.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_AUTHOR;

@DataJpaTest
@Import(AuthorDaoJPA.class)
class AuthorDaoJPATest {

    @Autowired
    private AuthorDaoJPA authorRepositoryJPA;


    @Test
    void findByName() {
        assertThat(authorRepositoryJPA.findByName("Веллер Михаил"))
          .isEqualTo(EXISTING_AUTHOR);
    }
}
