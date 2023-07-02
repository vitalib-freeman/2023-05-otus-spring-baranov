package ru.otus.homework.vitalib.utils;

import java.io.Reader;

public interface ReaderProvider {
    Reader getDataReader(String path);
}
