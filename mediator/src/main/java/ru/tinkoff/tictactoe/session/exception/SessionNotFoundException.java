package ru.tinkoff.tictactoe.session.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import ru.tinkoff.tictactoe.ApiException;

public class SessionNotFoundException extends ApiException {

    public SessionNotFoundException() {
        super("Сессия не найдена");
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
