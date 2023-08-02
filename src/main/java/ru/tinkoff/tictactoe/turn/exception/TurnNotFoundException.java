package ru.tinkoff.tictactoe.turn.exception;

import ru.tinkoff.tictactoe.ApiException;

public class TurnNotFoundException extends ApiException {
    public TurnNotFoundException() {
        super("Ход не найден");
    }
}
