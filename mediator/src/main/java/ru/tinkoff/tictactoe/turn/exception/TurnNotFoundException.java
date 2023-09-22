package ru.tinkoff.tictactoe.turn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import ru.tinkoff.tictactoe.ApiException;

public class TurnNotFoundException extends ApiException {

    public TurnNotFoundException() {
        super("Ход не найден");
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
