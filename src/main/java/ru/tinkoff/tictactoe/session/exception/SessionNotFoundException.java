package ru.tinkoff.tictactoe.session.exception;

import ru.tinkoff.tictactoe.ApiException;

public class SessionNotFoundException extends ApiException {
    public SessionNotFoundException() {
        super("Сессия не найдена");
    }
}
