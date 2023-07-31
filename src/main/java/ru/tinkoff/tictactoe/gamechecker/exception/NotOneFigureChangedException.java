package ru.tinkoff.tictactoe.gamechecker.exception;

public class NotOneFigureChangedException extends ValidCheckerException {
    public NotOneFigureChangedException() {
        super("Бот за свой ход изменил не одну клетку");
    }
}
