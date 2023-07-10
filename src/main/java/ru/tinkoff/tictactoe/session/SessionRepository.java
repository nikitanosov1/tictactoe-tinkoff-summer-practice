package ru.tinkoff.tictactoe.session;

import ru.tinkoff.tictactoe.session.model.Session;

import java.util.UUID;

public interface SessionRepository {
    Session createSession();

    Session findBySessionId(UUID sessionId);

    void setFirstBotId(UUID sessionId, UUID botId);

    void setSecondBotId(UUID sessionId, UUID botId);
}
