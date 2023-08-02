package ru.tinkoff.tictactoe.turn;

import ru.tinkoff.tictactoe.turn.model.Turn;

import java.util.UUID;

public interface TurnService {
    Turn getTurnBySessionIdAndTurn(UUID sessionId, Integer turn);
}
