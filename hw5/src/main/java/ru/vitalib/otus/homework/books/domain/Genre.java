package ru.vitalib.otus.homework.books.domain;

import lombok.Data;

@Data
public class Genre {
    private final String name;

    private long id;

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String name) {
        this.name = name;
    }
}
