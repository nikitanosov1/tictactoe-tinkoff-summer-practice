package ru.tinkoff.tictactoe.gamechecker.exception;

import ru.tinkoff.tictactoe.ApiException;

public class UnsupportedFigureException extends ApiException {
    public UnsupportedFigureException() {
        super("Передан неподдерживаемый тип фигуры");
    }
}
