package ru.tinkoff.tictactoe.session;

import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.model.SessionWithAllTurns;

import java.util.List;
import java.util.UUID;
import ru.tinkoff.tictactoe.session.model.SessionWithLastTurn;

public interface SessionService {
    Session createSession();

    Figure registerBotInSession(UUID sessionId, String url, String botId) throws InterruptedException;

    SessionWithLastTurn getSession(UUID sessionId);

    List<Session> getSessionsByIsActive(Boolean isActive);
}
