package ru.vitalib.otus.homework.books.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.vitalib.otus.homework.books.domain.Author;
import ru.vitalib.otus.homework.books.domain.Book;
import ru.vitalib.otus.homework.books.domain.Genre;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public long save(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(
          Map.of(
            "name", book.getName(),
            "genre_id", book.getGenre().getId(),
            "author_id", book.getAuthor().getId()
          )
        );
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
          "insert into book (name, genre_id, author_id) values(:name, :genre_id, :author_id)",
          parameterSource,
          keyHolder
        );
        return (long) keyHolder.getKey();
    }

    @Override
    public Book findById(long id) {
        try {
            return jdbc.queryForObject(
              "select b.id, b.name, g.id, g.name, a.id, a.name " +
                "from book as b " +
                "join genre as g on (b.genre_id = g.id) " +
                "join author as a on (b.author_id = a.id) " +
                "where b.id =:id",
              Map.of("id", id),
              getBookRowMapper()
            );
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    private RowMapper<Book> getBookRowMapper() {
        return (rs, rowNum) -> {
            Author author = new Author(rs.getLong("author.id"), rs.getString("author.name"));
            Genre genre = new Genre(rs.getLong("genre.id"), rs.getString("genre.name"));
            return new Book(rs.getLong("book.id"), rs.getString("book.name"), genre, author);
        };
    }

    @Override
    public void delete(long id) {
        jdbc.update("delete from book where id = :id", Map.of("id", id));

    }

    @Override
    public void update(long id, Book book) {
        jdbc.update(
          "update book set name = :name, author_id = :author_id, genre_id = :genre_id where id = :id",
          Map.of(
            "name", book.getName(),
            "id", id,
            "author_id", book.getAuthor().getId(),
            "genre_id", book.getGenre().getId()
          )
        );
    }

    @Override
    @SuppressWarnings("all")
    public int count() {
        return jdbc.queryForObject("select count(1) from book", Map.of(), Integer.class);
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query(
          "select b.id, b.name, g.id, g.name, a.id, a.name " +
            "from book as b " +
            "join genre as g on (b.genre_id = g.id) " +
            "join author as a on (b.author_id = a.id)",
          Map.of(),
          getBookRowMapper()
        );
    }
}
