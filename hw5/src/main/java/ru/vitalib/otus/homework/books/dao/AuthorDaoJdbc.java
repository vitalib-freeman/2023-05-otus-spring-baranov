package ru.vitalib.otus.homework.books.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.vitalib.otus.homework.books.domain.Author;

import java.util.Map;

@Repository
@AllArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Author findByName(String authorName) {
        try {
            return jdbc.queryForObject(
              "select id, name from author where name =:name",
              Map.of("name", authorName),
              (rs, rowNum) -> new Author(rs.getLong("id"), rs.getString("name"))
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
