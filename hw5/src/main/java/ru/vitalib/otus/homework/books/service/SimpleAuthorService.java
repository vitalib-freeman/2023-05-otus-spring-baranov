package ru.vitalib.otus.homework.books.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vitalib.otus.homework.books.dao.AuthorDao;
import ru.vitalib.otus.homework.books.domain.Author;
import ru.vitalib.otus.homework.books.exception.AuthorNotFoundException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAuthorService implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public Author getAuthorByName(String authorName) {
        return Optional.ofNullable(authorDao.findByName(authorName)).orElseThrow(AuthorNotFoundException::new);
    }
}
