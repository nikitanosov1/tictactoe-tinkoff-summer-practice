package ru.tinkoff.tictactoe.session.exception;

import ru.tinkoff.tictactoe.ApiException;

public class SessionIsAlreadyFullException extends ApiException {
    public SessionIsAlreadyFullException() {
        super("В сессии уже зарегистрировано два бота");
    }
}
