package ru.vitalib.otus.homework.books.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_AUTHOR;

@JdbcTest
@Import(AuthorDaoJdbc.class)
public class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc dao;

    @Test
    void findByName() {
        assertThat(dao.findByName("Веллер Михаил"))
          .isEqualTo(EXISTING_AUTHOR);
    }
}