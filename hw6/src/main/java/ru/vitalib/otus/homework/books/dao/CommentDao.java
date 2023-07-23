package ru.vitalib.otus.homework.books.dao;

import ru.vitalib.otus.homework.books.domain.Comment;

import java.util.List;

public interface CommentDao {
  Comment save(Comment comment);

  void delete(long commentId);

  Comment findById(Long commentId);

  List<Comment> findByBookId(long bookId);
}
