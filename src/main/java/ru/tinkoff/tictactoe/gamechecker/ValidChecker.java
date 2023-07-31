package ru.tinkoff.tictactoe.gamechecker;

import ru.tinkoff.tictactoe.session.Figure;

public interface ValidChecker {
    void validate(String currGameField, String newGameField, Figure figure);
}
