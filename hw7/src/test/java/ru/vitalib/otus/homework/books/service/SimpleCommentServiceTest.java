package ru.vitalib.otus.homework.books.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.vitalib.otus.homework.books.converter.CommentConverter;
import ru.vitalib.otus.homework.books.dao.BookRepository;
import ru.vitalib.otus.homework.books.dao.CommentRepository;
import ru.vitalib.otus.homework.books.domain.Book;
import ru.vitalib.otus.homework.books.domain.Comment;
import ru.vitalib.otus.homework.books.dto.CommentDto;
import ru.vitalib.otus.homework.books.exception.BookNotFoundException;
import ru.vitalib.otus.homework.books.exception.CommentNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Test comment service")
@SpringBootTest(classes = {SimpleCommentService.class, CommentConverter.class})
public
class SimpleCommentServiceTest {

    @Autowired
    private SimpleCommentService simpleCommentService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Create comment for existing book")
    public void createCommentForExistingBook() {
        String commentText = "comment text";
        long bookId = 1;
        Comment commentForSave = new Comment();
        commentForSave.setText(commentText);
        when(bookRepository.findById(bookId))
          .thenReturn(new Book(bookId, null, null, null));
        when(commentRepository.save(any()))
          .thenReturn(commentForSave);

        CommentDto comment = simpleCommentService.createComment(commentText, bookId);

        assertThat(comment)
          .matches(c -> c.text().equals(commentText));

        verify(bookRepository).findById(bookId);
        verify(commentRepository).save(ArgumentMatchers.argThat(c -> c.getText().equals(commentText)));
    }

    @Test
    @DisplayName("Create comment for non existing book")
    public void createCommentForNonExistingBook() {
        long bookId = 5;
        when(bookRepository.findById(bookId))
          .thenReturn(null);

        assertThatThrownBy(() -> simpleCommentService.createComment("comment text", bookId))
          .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    @DisplayName("Find comment by id")
    public void findCommentById() {
        long commentId = 1;
        Comment expectedComment = new Comment();
        expectedComment.setId(commentId);
        expectedComment.setText("expected text");
        when(commentRepository.findById(commentId)).thenReturn(expectedComment);

        CommentDto comment = simpleCommentService.getCommentById(commentId);

        assertThat(comment)
          .matches(c -> c.id() == expectedComment.getId())
          .matches(c -> c.text().equals(expectedComment.getText()));
    }

    @Test
    @DisplayName("Update comment")
    public void updateComment() {
        long commentId = 1;
        String newCommentText = "new comment text";
        Comment expectedComment = new Comment();
        expectedComment.setId(commentId);
        expectedComment.setText("previous text");
        when(commentRepository.findById(commentId))
          .thenReturn(expectedComment);

        simpleCommentService.updateComment(commentId, newCommentText);

        verify(commentRepository).save(ArgumentMatchers.argThat(c -> c.getId() == commentId && c.getText().equals(newCommentText)));
    }

    @Test
    @DisplayName("Update non-existing comment")
    public void updateNonExistingComment() {
        long commentId = 1;
        when(commentRepository.findById(commentId))
          .thenReturn(null);

        assertThatThrownBy(() -> simpleCommentService.updateComment(commentId, "newCommentText"))
          .isInstanceOf(CommentNotFoundException.class);
    }

    @Test
    @DisplayName("Delete comment")
    public void deleteComment() {
        long commentId = 1;

        simpleCommentService.deleteComment(commentId);

        verify(commentRepository).deleteById(commentId);
    }

    @Test
    @DisplayName("Get comments for book")
    public void getCommentsForBook() {
        long bookId = 1;
        when(commentRepository.findByBookId(bookId))
          .thenReturn(List.of(new Comment()));

        List<CommentDto> allBookComments = simpleCommentService.getBookComments(bookId);

        assertThat(allBookComments)
          .hasSize(1);
    }
}
