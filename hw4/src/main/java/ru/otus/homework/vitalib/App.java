package ru.otus.homework.vitalib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import ru.otus.homework.vitalib.shell.TestsRunner;

@SpringBootApplication
@EnableConfigurationProperties
@Import(TestsRunner.class)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
