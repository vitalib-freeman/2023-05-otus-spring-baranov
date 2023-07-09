package ru.vitalib.otus.homework.books.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Author {
    private final String name;

    private long id;

    public Author(String name) {
        this.name = name;
    }

    public Author(long id, String name) {
        this.name = name;
    }

}
