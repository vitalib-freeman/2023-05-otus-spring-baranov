package ru.vitalib.otus.homework.books.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.vitalib.otus.homework.books.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long save(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(Map.of("name", genre.getName()));
        jdbc.update(
          "insert into genre (name) values(:name)",
          parameterSource,
          keyHolder
        );
        return (long) keyHolder.getKey();
    }

    @Override
    public Genre findById(long id) {
        try {
            return jdbc.queryForObject(
              "select id, name from genre where id =:id",
              Map.of("id", id),
              new GenreRowMapper()
            );
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public void delete(long id) {
        jdbc.update("delete from genre where id = :id", Map.of("id", id));

    }

    @Override
    public void update(long id, Genre genre) {
        jdbc.update(
          "update genre set name = :name where id = :id",
          Map.of("name", genre.getName(), "id", genre.getId())
        );
    }

    @Override
    @SuppressWarnings("all")
    public int count() {
        return jdbc.queryForObject("select count(1) from genre", Map.of(), Integer.class);
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query(
          "select id, name from genre",
          new GenreRowMapper()
        );
    }

    @Override
    public Genre findByName(String genreName) {
        try {
            return jdbc.queryForObject(
              "select id, name from genre where name =:name",
              Map.of("name", genreName),
              new GenreRowMapper()
            );
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    private static class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getLong("id"), rs.getString("name"));
        }
    }

}
