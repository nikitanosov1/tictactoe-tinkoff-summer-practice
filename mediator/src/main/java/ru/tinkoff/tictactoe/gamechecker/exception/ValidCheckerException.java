package ru.tinkoff.tictactoe.gamechecker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import ru.tinkoff.tictactoe.ApiException;

public class ValidCheckerException extends ApiException {

    public ValidCheckerException(String message) {
        super(message);
    }

    public ValidCheckerException() {
        super("Не прошла валидация");
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
