package ru.vitalib.otus.homework.books.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vitalib.otus.homework.books.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  Comment findById(long commentId);

  List<Comment> findByBookId(long bookId);

  void deleteByBookId(long id);
}
