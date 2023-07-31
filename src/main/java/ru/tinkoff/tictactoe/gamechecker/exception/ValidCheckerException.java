package ru.tinkoff.tictactoe.gamechecker.exception;

import ru.tinkoff.tictactoe.ApiException;

public class ValidCheckerException extends ApiException {
    public ValidCheckerException(String message) {
        super(message);
    }

    public ValidCheckerException() {
        super("Не прошла валидация");
    }
}
