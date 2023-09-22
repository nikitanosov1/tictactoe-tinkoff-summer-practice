package ru.tinkoff.tictactoe.gamechecker.exception;

public class IncorrectFigureException extends ValidCheckerException {

    public IncorrectFigureException() {
        super("Передана некорректная фигура");
    }

}
