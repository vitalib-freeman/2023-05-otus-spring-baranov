package ru.vitalib.otus.homework.books.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitalib.otus.homework.books.converter.BookConverter;
import ru.vitalib.otus.homework.books.dao.BookRepository;
import ru.vitalib.otus.homework.books.dao.CommentRepository;
import ru.vitalib.otus.homework.books.domain.Author;
import ru.vitalib.otus.homework.books.domain.Book;
import ru.vitalib.otus.homework.books.domain.Genre;
import ru.vitalib.otus.homework.books.dto.BookDto;
import ru.vitalib.otus.homework.books.exception.BookNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SimpleBookService implements BookService {

    private final BookRepository bookRepository;

    private final GenreService genreService;

    private final AuthorService authorService;

    private final BookConverter bookConverter;

    private final CommentRepository commentRepository;


    @Transactional
    @Override
    public BookDto createBook(String title, String authorName, String genreName) {
        Author author = authorService.getAuthorByName(authorName);
        Genre genre = genreService.getGenreByName(genreName);
        Book book = new Book(title, genre, author);
        bookRepository.save(book);
        return bookConverter.convert(book);
    }

    @Transactional
    @Override
    public void deleteBook(long id) {
        commentRepository.deleteByBookId(id);
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateBook(long id, String title, String authorName, String genreName) {
        Book book = Optional.ofNullable(bookRepository.findById(id)).orElseThrow(BookNotFoundException::new);
        Author author = authorService.getAuthorByName(authorName);
        Genre genre = genreService.getGenreByName(genreName);
        book.setGenre(genre);
        book.setAuthor(author);
        book.setName(title);
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(bookConverter::convert).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    @Override
    public BookDto getBookById(long id) {
        return Optional.ofNullable(bookRepository.findById(id))
          .map(bookConverter::convert)
          .orElseThrow(BookNotFoundException::new);
    }
}
