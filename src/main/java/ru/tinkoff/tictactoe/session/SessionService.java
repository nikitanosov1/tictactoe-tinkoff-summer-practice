package ru.tinkoff.tictactoe.session;

import java.util.UUID;

public interface SessionService {
    Session createSession();

    Figure registerBotInSession(UUID sessionId, UUID botId);
}
