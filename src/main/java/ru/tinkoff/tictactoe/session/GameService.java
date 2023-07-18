package ru.tinkoff.tictactoe.session;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface GameService {
    CompletableFuture<String> startGame(UUID sessionId);
}
