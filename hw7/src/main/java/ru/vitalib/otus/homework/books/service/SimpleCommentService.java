package ru.vitalib.otus.homework.books.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vitalib.otus.homework.books.converter.CommentConverter;
import ru.vitalib.otus.homework.books.dao.BookDao;
import ru.vitalib.otus.homework.books.dao.CommentDao;
import ru.vitalib.otus.homework.books.domain.Book;
import ru.vitalib.otus.homework.books.domain.Comment;
import ru.vitalib.otus.homework.books.dto.CommentDto;
import ru.vitalib.otus.homework.books.exception.BookNotFoundException;
import ru.vitalib.otus.homework.books.exception.CommentNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleCommentService implements CommentService {
    private final CommentDao commentDao;

    private final BookDao bookDao;

    private final CommentConverter commentConverter;

    @Transactional
    @Override
    public CommentDto createComment(String commentText, long bookId) {
        Book book = Optional.ofNullable(bookDao.findById(bookId)).orElseThrow(BookNotFoundException::new);
        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setBook(book);
        return commentConverter.convert(commentDao.save(comment));
    }

    @Transactional
    @Override
    public void updateComment(long commentId, String newCommentText) {
        Comment comment = Optional.ofNullable(commentDao.findById(commentId))
          .orElseThrow(CommentNotFoundException::new);
        comment.setText(newCommentText);
        commentDao.save(comment);
    }

    @Transactional
    @Override
    public void deleteComment(long commentId) {
        commentDao.deleteById(commentId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getBookComments(long bookId) {
        return commentDao.findByBookId(bookId).stream().map(commentConverter::convert).toList();
    }


    @Transactional(readOnly = true)
    @Override
    public CommentDto getCommentById(long commentId) {
        return Optional.ofNullable(commentDao.findById(commentId))
          .map(commentConverter::convert)
          .orElseThrow(CommentNotFoundException::new);
    }
}
