package ru.vitalib.otus.homework.books.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vitalib.otus.homework.books.dao.BookDao;
import ru.vitalib.otus.homework.books.domain.Author;
import ru.vitalib.otus.homework.books.domain.Book;
import ru.vitalib.otus.homework.books.domain.Genre;
import ru.vitalib.otus.homework.books.exception.BookNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleBookService implements BookService {

    private final BookDao bookDao;

    private final GenreService genreService;

    private final AuthorService authorService;


    @Override
    public Book createBook(String title, String authorName, String genreName) {
        Author author = authorService.getAuthorByName(authorName);
        Genre genre = genreService.getGenreByName(genreName);
        Book book = new Book(title, genre, author);
        bookDao.save(book);
        return book;
    }

    @Override
    public void deleteBook(long id) {
        bookDao.delete(id);
    }

    @Override
    public void updateBook(long id, String title, String authorName, String genreName) {
        Book book = getBookById(id);
        Author author = authorService.getAuthorByName(authorName);
        Genre genre = genreService.getGenreByName(genreName);
        bookDao.update(book.getId(), new Book(title, genre, author));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public Book getBookById(long id) {
        return Optional.ofNullable(bookDao.findById(id)).orElseThrow(BookNotFoundException::new);
    }
}
