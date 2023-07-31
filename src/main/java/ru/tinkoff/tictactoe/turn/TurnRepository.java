package ru.tinkoff.tictactoe.turn;

import ru.tinkoff.tictactoe.turn.model.Turn;

import java.util.UUID;

public interface TurnRepository {
    Turn findTurnBySessionIdAndTurn(UUID sessionId, Integer turn);
}
