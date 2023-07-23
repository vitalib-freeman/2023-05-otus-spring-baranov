package ru.vitalib.otus.homework.books.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitalib.otus.homework.books.domain.Author;

@Repository
public interface AuthorDao extends JpaRepository<Author, Long> {
    Author findByName(String authorName);
}
