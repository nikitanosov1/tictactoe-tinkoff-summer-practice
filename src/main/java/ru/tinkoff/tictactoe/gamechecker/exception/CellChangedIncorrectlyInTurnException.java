package ru.tinkoff.tictactoe.gamechecker.exception;

public class CellChangedIncorrectlyInTurnException extends ValidCheckerException {
    public CellChangedIncorrectlyInTurnException() {
        super("Бот изменил клетку некорректно");
    }
}
