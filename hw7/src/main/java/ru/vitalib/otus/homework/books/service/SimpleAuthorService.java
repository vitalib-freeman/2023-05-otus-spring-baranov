package ru.vitalib.otus.homework.books.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitalib.otus.homework.books.dao.AuthorRepository;
import ru.vitalib.otus.homework.books.domain.Author;
import ru.vitalib.otus.homework.books.exception.AuthorNotFoundException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAuthorService implements AuthorService {

    private final AuthorRepository authorRepository;


    @Transactional(readOnly = true)
    @Override
    public Author getAuthorByName(String authorName) {
        return Optional.ofNullable(authorRepository.findByName(authorName)).orElseThrow(AuthorNotFoundException::new);
    }
}
