package ru.vitalib.otus.homework.books.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_AUTHOR;

@DataJpaTest
class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;


    @Test
    void findByName() {
        assertThat(authorDao.findByName("Веллер Михаил"))
          .isEqualTo(EXISTING_AUTHOR);
    }
}
