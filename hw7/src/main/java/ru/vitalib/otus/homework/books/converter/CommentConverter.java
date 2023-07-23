package ru.vitalib.otus.homework.books.converter;

import org.springframework.stereotype.Service;
import ru.vitalib.otus.homework.books.domain.Comment;
import ru.vitalib.otus.homework.books.dto.CommentDto;

@Service
public class CommentConverter {
    public CommentDto convert(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText());
    }
}
