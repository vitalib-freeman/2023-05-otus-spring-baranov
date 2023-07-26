package ru.vitalib.otus.homework.books.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitalib.otus.homework.books.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @EntityGraph(attributePaths = {"genre", "author"})
    List<Book> findAll();

    Book findById(long id);
}
