package ru.vitalib.otus.homework.books.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitalib.otus.homework.books.dao.GenreDao;
import ru.vitalib.otus.homework.books.domain.Genre;
import ru.vitalib.otus.homework.books.exception.GenreNotFoundException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleGenreService implements GenreService {

    private final GenreDao genreDao;


    @Transactional(readOnly = true)
    @Override
    public Genre getGenreByName(String genreName) {
        return Optional.ofNullable(genreDao.findByName(genreName)).orElseThrow(GenreNotFoundException::new);
    }
}
