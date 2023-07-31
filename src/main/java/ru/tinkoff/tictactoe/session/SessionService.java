package ru.tinkoff.tictactoe.session;

import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.turn.model.StateOfSession;

import java.util.List;
import java.util.UUID;

public interface SessionService {
    Session createSession();

    Figure registerBotInSession(UUID sessionId, UUID botId);

    Session getSession(UUID sessionId);

    StateOfSession getStateOfSessionByTurn(UUID sessionId, Integer turn);

    List<Session> getSessionsByIsActive(Boolean isActive);
}
