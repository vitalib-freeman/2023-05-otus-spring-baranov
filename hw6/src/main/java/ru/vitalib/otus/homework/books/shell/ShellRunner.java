package ru.vitalib.otus.homework.books.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.vitalib.otus.homework.books.dto.BookDto;
import ru.vitalib.otus.homework.books.dto.CommentDto;
import ru.vitalib.otus.homework.books.exception.AuthorNotFoundException;
import ru.vitalib.otus.homework.books.exception.BookNotFoundException;
import ru.vitalib.otus.homework.books.exception.CommentNotFoundException;
import ru.vitalib.otus.homework.books.exception.GenreNotFoundException;
import ru.vitalib.otus.homework.books.service.BookService;
import ru.vitalib.otus.homework.books.service.CommentService;

import java.util.List;

import static java.util.stream.Collectors.joining;

@ShellComponent
@RequiredArgsConstructor
public class ShellRunner {

    private final BookService bookService;

    private final CommentService commentService;

    @ShellMethod(value = "View all books", key = {"book all", "ba"})
    public String viewAllBooks() {
        List<BookDto> allBooks = bookService.getAllBooks();
        if (allBooks.isEmpty()) {
            return "No books found";
        }
        return allBooks.stream().map(this::getBookView).collect(joining("\n"));
    }

    @ShellMethod(value = "Add book", key = {"book create", "bc"})
    public String createBook(@ShellOption("title") String bookTitle,
                             @ShellOption("author") String author,
                             @ShellOption("genre") String genre) {

        try {
            BookDto book = bookService.createBook(bookTitle, author, genre);
            return String.format("Book with id %d is created", book.id());
        } catch (BookNotFoundException ex) {
            return "Error: Book doesn't exist";
        } catch (GenreNotFoundException ex) {
            return "Error: Genre doesn't exist";
        } catch (AuthorNotFoundException ex) {
            return "Error: Author doesn't exist";
        }
    }

    @ShellMethod(value = "Update book", key = {"book update", "bu"})
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

    @ShellMethod(value = "Delete book", key = {"book delete", "bd"})
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

    @ShellMethod(value = "View book comments", key = {"book comments all", "bca"})
    public String viewBookComments(@ShellOption("bookId") Long bookId) {
        try {
            List<CommentDto> bookComments = commentService.getBookComments(bookId);
            if (bookComments.isEmpty()) {
                return "No comments found";
            }
            return bookComments
              .stream()
              .map(c -> "%s (id: %s)".formatted(c.text(), c.id()))
              .collect(joining("\n"));
        } catch (BookNotFoundException ex) {
            return "Error: Book doesn't exist";
        }
    }

    @ShellMethod(value = "Create book comment", key = {"book comment create", "bcc"})
    public String createBookComment(@ShellOption("text") String text, @ShellOption("bookId") Long bookId) {
        try {
            CommentDto comment = commentService.createComment(text, bookId);
            return "Comment [%d] created".formatted(comment.id());
        } catch (BookNotFoundException ex) {
            return "Error: Book doesn't exist";
        }
    }


    @ShellMethod(value = "Update book comment", key = {"book comment update", "bcu"})
    public String updateBookComment(@ShellOption("text") String text, @ShellOption("commentId") Long commentId) {
        try {
            commentService.updateComment(commentId, text);
            return "Comment [%d] updated".formatted(commentId);
        } catch (CommentNotFoundException ex) {
            return "Error: Comment doesn't exist";
        }
    }

    @ShellMethod(value = "Delete book comment", key = {"book comment delete", "bcd"})
    public String deleteBookComment(@ShellOption("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return "Comment [%d] deleted".formatted(commentId);
    }


    @ShellMethod(value = "View book comment", key = {"book comments single", "bcs"})
    public String viewBookComment(@ShellOption("commentId") Long bookId) {
        try {
            return "%s".formatted(commentService.getCommentById(bookId).text());
        } catch (CommentNotFoundException ex) {
            return "Error: Comment doesn't exist";
        }
    }


    private String getBookView(BookDto book) {
        return
          String.format("Id: %d, Title: %s, Author: %s, Genre: %s",
            book.id(),
            book.title(),
            book.author(),
            book.genre()
          );
    }
}
