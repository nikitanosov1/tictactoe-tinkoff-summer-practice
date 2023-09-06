package ru.tinkoff.tictactoe.session;

import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.turn.model.Turn;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

public interface SessionRepository {
    Session createSession();

    Session findBySessionId(UUID sessionId);

    void setFirstBotIpAndFirstBotPort(UUID sessionId, InetAddress botIp, Integer botPort);

    void setSecondBotIpAndSecondBotPort(UUID sessionId, InetAddress botIp, Integer botPort);

    void addTurnToSession(UUID sessionId, Turn turn);

    List<Session> findAllSessions();

    List<Session> findSessionsByIsActive(Boolean isActive);
}
