package ru.tinkoff.tictactoe.session.exception;

public class ApiException extends RuntimeException {
    public ApiException(ApiError apiError) {
        super(apiError.getName());
    }
}
