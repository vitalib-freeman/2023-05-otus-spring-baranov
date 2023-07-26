package ru.vitalib.otus.homework.books.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitalib.otus.homework.books.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String authorName);
}
