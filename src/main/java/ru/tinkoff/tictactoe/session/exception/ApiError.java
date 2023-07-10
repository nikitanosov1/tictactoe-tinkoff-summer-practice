package ru.tinkoff.tictactoe.session.exception;

public enum ApiError {
    SESSION_NOT_FOUND("Сессия не найдена"),
    SESSION_IS_ALREADY_FULL("В сессии уже зарегистрировано два бота");

    private final String message;

    ApiError(String message) {
        this.message = message;
    }

    public String getName() {
        return message;
    }
}
