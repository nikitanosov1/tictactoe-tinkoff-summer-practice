package ru.tinkoff.tictactoe.gamechecker;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.tinkoff.tictactoe.gamechecker.exception.GameFieldIncorrectSizeException;
import ru.tinkoff.tictactoe.session.Figure;

@Component
@Order(1)
public class CorrectSizeValidChecker implements ValidChecker {
    @Override
    public void validate(String currGameField, String newGameField, Figure figure) {
        if (newGameField == null || newGameField.length() != 19 * 19) {
            throw new GameFieldIncorrectSizeException();
        }
    }
}
