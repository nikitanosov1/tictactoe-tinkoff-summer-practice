package ru.tinkoff.tictactoe.session;

import ru.tinkoff.tictactoe.session.model.Session;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface GameService {
    CompletableFuture<Session> startGame(UUID sessionId);
}
