package ru.tinkoff.tictactoe.gamechecker.exception;

public class GameFieldIncorrectSizeException extends ValidCheckerException {
    public GameFieldIncorrectSizeException() {
        super("Игровое поле отсутствует или его размер не равен 361");
    }
}
