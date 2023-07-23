package ru.vitalib.otus.homework.books.converter;

import org.springframework.stereotype.Service;
import ru.vitalib.otus.homework.books.domain.Book;
import ru.vitalib.otus.homework.books.dto.BookDto;

@Service
public class BookConverter {

    public BookDto convert(Book book) {
        return new BookDto(
          book.getId(), book.getGenre().getName(), book.getGenre().getName(), book.getAuthor().getName()
        );
    }
}
