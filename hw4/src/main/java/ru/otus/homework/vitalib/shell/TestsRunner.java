package ru.otus.homework.vitalib.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.vitalib.service.CliRunner;
import ru.otus.homework.vitalib.service.MessageProvider;

import java.util.Objects;

@ShellComponent
@RequiredArgsConstructor
public class TestsRunner {
    private final CliRunner cliRunner;

    private final MessageProvider messageProvider;

    private String userName;

    private String userPassword;

    @ShellMethod(value = "Start testing command", key = {"t", "test"})
    @ShellMethodAvailability("isUserLoggedIn")
    public void startTesting() {
        cliRunner.run();
    }


    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption("userName") String userName, @ShellOption("password") String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
        return String.format(messageProvider.getTranslation("strings.greeting", userName));
    }

    private Availability isUserLoggedIn() {
        return Objects.nonNull(userName) && Objects.nonNull(userPassword)
          ? Availability.available()
          : Availability.unavailable(messageProvider.getTranslation("strings.login"));
    }
}
