package ru.otus.homework.vitalib.service;

import org.springframework.stereotype.Service;

import java.io.PrintWriter;

@Service
public class CliWriter implements Writer {
    private final PrintWriter writer;

    public CliWriter() {
        writer = new PrintWriter(System.out);
    }

    @Override
    public void write(String data) {
        writer.write(data);
        writer.flush();
    }
}
