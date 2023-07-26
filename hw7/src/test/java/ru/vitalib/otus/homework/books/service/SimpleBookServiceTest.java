package ru.vitalib.otus.homework.books.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.vitalib.otus.homework.books.converter.BookConverter;
import ru.vitalib.otus.homework.books.dao.BookRepository;
import ru.vitalib.otus.homework.books.dao.CommentRepository;
import ru.vitalib.otus.homework.books.dto.BookDto;
import ru.vitalib.otus.homework.books.exception.AuthorNotFoundException;
import ru.vitalib.otus.homework.books.exception.BookNotFoundException;
import ru.vitalib.otus.homework.books.exception.GenreNotFoundException;
import ru.vitalib.otus.homework.books.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_AUTHOR;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_BOOK;
import static ru.vitalib.otus.homework.books.PreInsertedTestData.EXISTING_GENRE;

@DisplayName("Test book service")
@SpringBootTest(classes = {SimpleBookService.class, BookConverter.class})
public
class SimpleBookServiceTest {

    @Autowired
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    @MockBean
    CommentRepository commentRepository;

    @Test
    @DisplayName("Get all books")
    void getAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(EXISTING_BOOK));
        List<BookDto> allBooks = bookService.getAllBooks();

        assertThat(allBooks)
          .hasSize(1)
          .element(0)
          .matches(b -> b.id() == EXISTING_BOOK.getId());
    }

    @Test
    void saveBookWithExistentGenreAndAuthor() {
        when(authorService.getAuthorByName(EXISTING_AUTHOR.getName())).thenReturn(EXISTING_AUTHOR);
        when(genreService.getGenreByName(EXISTING_GENRE.getName())).thenReturn(EXISTING_GENRE);

        bookService.createBook("New Book Title", EXISTING_AUTHOR.getName(), EXISTING_GENRE.getName());

        verify(authorService).getAuthorByName(EXISTING_AUTHOR.getName());
        verify(genreService).getGenreByName(EXISTING_GENRE.getName());
        verify(bookRepository).save(any());
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
        when(bookRepository.findById(EXISTING_BOOK.getId())).thenReturn(EXISTING_BOOK);
        when(authorService.getAuthorByName(EXISTING_AUTHOR.getName())).thenReturn(EXISTING_AUTHOR);
        when(genreService.getGenreByName(EXISTING_GENRE.getName())).thenReturn(EXISTING_GENRE);

        bookService.updateBook(EXISTING_BOOK.getId(), "OtherTitle", EXISTING_AUTHOR.getName(), EXISTING_GENRE.getName());

        verify(bookRepository).findById(EXISTING_BOOK.getId());
        verify(authorService).getAuthorByName(EXISTING_AUTHOR.getName());
        verify(genreService).getGenreByName(EXISTING_GENRE.getName());
        verify(bookRepository).save(ArgumentMatchers.argThat(b -> b.getName().equals("OtherTitle")));
    }

    @Test
    @DisplayName("Update non-existent book throw error")
    void updateNonExistentBook() {
        when(bookRepository.findById(EXISTING_BOOK.getId())).thenReturn(null);

        assertThatThrownBy(() -> bookService.updateBook(EXISTING_BOOK.getId(), "OtherTitle", EXISTING_AUTHOR.getName(), EXISTING_GENRE.getName()))
          .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Delete book")
    void deleteBook() {
        bookService.deleteBook(EXISTING_BOOK.getId());

        verify(bookRepository).deleteById(EXISTING_BOOK.getId());
        verify(commentRepository).deleteByBookId(EXISTING_BOOK.getId());
    }

    @Test
    @DisplayName(("Get book by id"))
    public void getBookById() {
        assertThatThrownBy(() -> bookService.getBookById(EXISTING_BOOK.getId()))
          .isInstanceOf(BookNotFoundException.class);
        verify(bookRepository).findById(EXISTING_BOOK.getId());
    }
}
