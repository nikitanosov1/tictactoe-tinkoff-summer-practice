package ru.tinkoff.tictactoe.gamechecker;

import ru.tinkoff.tictactoe.session.Figure;

public interface WinChecker {
    WinCheckerResults check(String gameField, Figure figure);
}
