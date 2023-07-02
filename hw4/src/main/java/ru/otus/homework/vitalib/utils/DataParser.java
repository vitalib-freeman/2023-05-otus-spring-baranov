package ru.otus.homework.vitalib.utils;

import java.io.Reader;
import java.util.List;

public interface DataParser {
    List<String[]> getLines(Reader reader);
}

