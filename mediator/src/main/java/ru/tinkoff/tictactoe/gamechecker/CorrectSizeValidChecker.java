package ru.tinkoff.tictactoe.gamechecker;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.tinkoff.tictactoe.gamechecker.exception.GameFieldIncorrectSizeException;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.impl.GameConfigs;

@Component
@Order(1)
public class CorrectSizeValidChecker implements ValidChecker {

    private final int flattenFieldSize;

    public CorrectSizeValidChecker(GameConfigs gameConfigs) {
        flattenFieldSize = gameConfigs.gameFieldSize() * gameConfigs.gameFieldSize();
    }

    @Override
    public void validate(String currGameField, String newGameField, Figure figure) {
        if (newGameField == null || newGameField.length() != flattenFieldSize) {
            throw new GameFieldIncorrectSizeException();
        }
    }
}
