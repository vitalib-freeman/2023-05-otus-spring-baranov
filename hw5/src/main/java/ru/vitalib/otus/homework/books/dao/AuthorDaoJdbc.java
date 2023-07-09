package ru.vitalib.otus.homework.books.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.vitalib.otus.homework.books.domain.Author;

import java.util.List;
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

    @Override
    public long save(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(Map.of("name", author.getName()));
        jdbc.update(
          "insert into author (name) values(:name)",
          parameterSource,
          keyHolder
        );
        return (long) keyHolder.getKey();
    }

    @Override
    public Author findById(long id) {
        try {
            return jdbc.queryForObject(
              "select id, name from author where id =:id",
              Map.of("id", id),
              (rs, rowNum) -> new Author(rs.getLong("id"), rs.getString("name"))
            );
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public void delete(long id) {
        jdbc.update("delete from author where id = :id", Map.of("id", id));

    }

    @Override
    public void update(long id, Author author) {
        jdbc.update(
          "update author set name = :name where id = :id",
          Map.of("name", author.getName(), "id", author.getId())
        );
    }

    @Override
    @SuppressWarnings("all")
    public int count() {
        return jdbc.queryForObject("select count(1) from author", Map.of(), Integer.class);
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query(
          "select id, name from author",
          (rs, rowNum) -> new Author(rs.getLong("id"), rs.getString("name"))
        );
    }
}
