package ru.vitalib.otus.homework.books.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.vitalib.otus.homework.books.domain.Book;
import ru.vitalib.otus.homework.books.exception.AuthorNotFoundException;
import ru.vitalib.otus.homework.books.exception.BookNotFoundException;
import ru.vitalib.otus.homework.books.exception.GenreNotFoundException;
import ru.vitalib.otus.homework.books.service.BookService;

import java.util.List;

import static java.util.stream.Collectors.joining;

@ShellComponent
@RequiredArgsConstructor
public class ShellRunner {

    private final BookService bookService;

    @ShellMethod(value = "View all books", key = {"all", "a"})
    public String viewAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();
        if (allBooks.isEmpty()) {
            return "No books found";
        }
        return allBooks.stream().map(this::getBookView).collect(joining("\n"));
    }

    @ShellMethod(value = "Add book", key = {"create", "c"})
    public String createBook(@ShellOption("title") String bookTitle,
                             @ShellOption("author") String author,
                             @ShellOption("genre") String genre) {

        try {
            Book book = bookService.createBook(bookTitle, author, genre);
            return String.format("Book with id %d is created", book.getId());
        } catch (BookNotFoundException ex) {
            return "Error: Book doesn't exist";
        } catch (GenreNotFoundException ex) {
            return "Error: Genre doesn't exist";
        } catch (AuthorNotFoundException ex) {
            return "Error: Author doesn't exist";
        }
    }

    @ShellMethod(value = "Update book", key = {"update", "u"})
    public String updateBook(
      @ShellOption("id") Long bookId,
      @ShellOption("title") String bookTitle,
      @ShellOption("author") String author,
      @ShellOption("genre") String genre) {
        try {
            bookService.updateBook(bookId, bookTitle, author, genre);
            return String.format("Book with id %d is updated", bookId);
        } catch (BookNotFoundException ex) {
            return "Error: Book doesn't exist";
        } catch (GenreNotFoundException ex) {
            return "Error: Genre doesn't exist";
        } catch (AuthorNotFoundException ex) {
            return "Error: Author doesn't exist";
        }
    }

    @ShellMethod(value = "Delete book", key = {"delete", "d"})
    public String deleteBook(@ShellOption("id") Long bookId) {
        bookService.deleteBook(bookId);
        return "Book[%d] successfully deleted".formatted(bookId);
    }

    @ShellMethod(value = "View book", key = {"book", "b"})
    public String viewBook(@ShellOption("id") Long bookId) {
        try {
            return getBookView(bookService.getBookById(bookId));
        } catch (BookNotFoundException ex) {
            return "Error: Book doesn't exist";
        }
    }

    private String getBookView(Book book) {
        return
          String.format("Id: %d, Title: %s, Author: %s, Genre: %s",
            book.getId(),
            book.getName(),
            book.getAuthor().getName(),
            book.getGenre().getName()
          );
    }
}
