package ru.otus.homework.vitalib.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CliReader implements Reader {
    private final Scanner scanner;

    public CliReader() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
