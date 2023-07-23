package ru.vitalib.otus.homework.books.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitalib.otus.homework.books.domain.Book;

public interface BookDao extends JpaRepository<Book, Long> {
    Book findById(long id);
}
