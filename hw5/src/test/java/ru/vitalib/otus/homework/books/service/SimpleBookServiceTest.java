package ru.vitalib.otus.homework.books.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.vitalib.otus.homework.books.dao.BookDao;
import ru.vitalib.otus.homework.books.domain.Book;
import ru.vitalib.otus.homework.books.exception.AuthorNotFoundException;
import ru.vitalib.otus.homework.books.exception.BookNotFoundException;
import ru.vitalib.otus.homework.books.exception.GenreNotFoundException;
import ru.vitalib.otus.homework.books.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.*;

@DisplayName("Test book service")
@SpringBootTest(classes = {SimpleBookService.class})
public
class SimpleBookServiceTest {

    @Autowired
    BookService bookService;
    @MockBean
    BookDao bookDao;
    @MockBean
    AuthorService authorService;
    @MockBean
    GenreService genreService;

    @Test
    @DisplayName("Get all books")
    void getAllBooks() {
        when(bookDao.findAll()).thenReturn(List.of(EXISTING_BOOK));
        List<Book> allBooks = bookService.getAllBooks();

        assertThat(allBooks)
          .containsAll(List.of(EXISTING_BOOK));
    }

    @Test
    void saveBookWithExistentGenreAndAuthor() {
        when(authorService.getAuthorByName(EXISTING_AUTHOR.getName())).thenReturn(EXISTING_AUTHOR);
        when(genreService.getGenreByName(EXISTING_GENRE.getName())).thenReturn(EXISTING_GENRE);

        bookService.createBook("New Book Title", EXISTING_AUTHOR.getName(), EXISTING_GENRE.getName());

        verify(authorService).getAuthorByName(EXISTING_AUTHOR.getName());
        verify(genreService).getGenreByName(EXISTING_GENRE.getName());
        verify(bookDao).save(any());
    }

    @Test
    void saveBookWithNonExistentAuthor() {
        when(authorService.getAuthorByName(EXISTING_AUTHOR.getName())).thenThrow(AuthorNotFoundException.class);
        when(genreService.getGenreByName(EXISTING_GENRE.getName())).thenReturn(EXISTING_GENRE);

        assertThatThrownBy(() -> bookService.createBook("New Book Title", EXISTING_AUTHOR.getName(), EXISTING_GENRE.getName()))
          .isInstanceOf(NotFoundException.class);
    }

    @Test
    void saveBookWithNonExistentGenre() {
        when(authorService.getAuthorByName(EXISTING_AUTHOR.getName())).thenReturn(EXISTING_AUTHOR);
        when(genreService.getGenreByName(EXISTING_GENRE.getName())).thenThrow(GenreNotFoundException.class);

        assertThatThrownBy(() -> bookService.createBook("New Book Title", EXISTING_AUTHOR.getName(), EXISTING_GENRE.getName()))
          .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Update book")
    void updateBookWithExistentGenreAndAuthor() {
        when(bookDao.findById(EXISTING_BOOK.getId())).thenReturn(EXISTING_BOOK);
        when(authorService.getAuthorByName(EXISTING_AUTHOR.getName())).thenReturn(EXISTING_AUTHOR);
        when(genreService.getGenreByName(EXISTING_GENRE.getName())).thenReturn(EXISTING_GENRE);

        bookService.updateBook(EXISTING_BOOK.getId(), "OtherTitle", EXISTING_AUTHOR.getName(), EXISTING_GENRE.getName());

        verify(bookDao).findById(EXISTING_BOOK.getId());
        verify(authorService).getAuthorByName(EXISTING_AUTHOR.getName());
        verify(genreService).getGenreByName(EXISTING_GENRE.getName());
        verify(bookDao).update(eq(EXISTING_BOOK.getId()), any());
    }

    @Test
    @DisplayName("Update non-existent book throw error")
    void updateNonExistentBook() {
        when(bookDao.findById(EXISTING_BOOK.getId())).thenReturn(null);

        assertThatThrownBy(() -> bookService.updateBook(EXISTING_BOOK.getId(), "OtherTitle", EXISTING_AUTHOR.getName(), EXISTING_GENRE.getName()))
          .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Delete book")
    void deleteBook() {
        bookService.deleteBook(EXISTING_BOOK.getId());

        verify(bookDao).delete(EXISTING_BOOK.getId());
    }

    @Test
    @DisplayName(("Get book by id"))
    public void getBookById() {
        assertThatThrownBy(() -> bookService.getBookById(EXISTING_BOOK.getId()))
          .isInstanceOf(BookNotFoundException.class);
        verify(bookDao).findById(EXISTING_BOOK.getId());
    }
}