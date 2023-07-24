package ru.tinkoff.tictactoe.gamechecker;

import ru.tinkoff.tictactoe.session.Figure;

public interface ValidChecker {
    ValidCheckerResults check(String currGameField, String newGameField, Figure figure);
}
