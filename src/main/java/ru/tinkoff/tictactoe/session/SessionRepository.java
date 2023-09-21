package ru.tinkoff.tictactoe.session;

import java.util.List;
import java.util.UUID;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.model.SessionWithAllTurns;
import ru.tinkoff.tictactoe.session.model.SessionWithLastTurn;
import ru.tinkoff.tictactoe.turn.model.Turn;

public interface SessionRepository {

    Session createSession();

    Session findBySessionId(UUID sessionId);

    SessionWithAllTurns findFetchTurnsBySessionId(UUID sessionId);

    SessionWithLastTurn findFetchLastTurnBySessionId(UUID sessionId);

    void setAttackingBot(UUID sessionId, String url, String id);

    void setDefendingBot(UUID sessionId, String url, String id);

    void addTurnToSession(UUID sessionId, Turn turn);

    List<Session> findAllSessions();

    List<Session> findSessionsByIsActive(Boolean isActive);

    void finishSession(UUID sessionId, String winBot);
}
