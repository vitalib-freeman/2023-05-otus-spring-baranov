package ru.vitalib.otus.homework.books.service;


import ru.vitalib.otus.homework.books.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(String commentText, long bookId);

    void updateComment(long commentId, String newCommentText);

    void deleteComment(long commentId);

    List<CommentDto> getBookComments(long bookId);

    CommentDto getCommentById(long commentId);
}
