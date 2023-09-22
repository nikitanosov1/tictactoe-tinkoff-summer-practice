package ru.tinkoff.tictactoe.session.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import ru.tinkoff.tictactoe.ApiException;

public class SessionIsAlreadyFullException extends ApiException {

    public SessionIsAlreadyFullException() {
        super("В сессии уже зарегистрировано два бота");
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.CONFLICT;
    }
}
