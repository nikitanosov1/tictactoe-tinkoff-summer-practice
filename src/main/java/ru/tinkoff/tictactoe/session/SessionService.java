package ru.tinkoff.tictactoe.session;

import ru.tinkoff.tictactoe.session.model.Session;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

public interface SessionService {
    Session createSession();

    Figure registerBotInSession(UUID sessionId, InetAddress botIp, Integer botPort);

    Session getSession(UUID sessionId);

    List<Session> getSessionsByIsActive(Boolean isActive);
}
