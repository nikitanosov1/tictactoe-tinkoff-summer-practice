package ru.tinkoff.tictactoe.session.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.tictactoe.session.*;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.turn.model.StateOfSession;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public Session createSession() {
        // TODO: реализовать создание сессии
        return null;
    }

    @Override
    public Figure registerBotInSession(UUID sessionId, UUID botId) {
        // TODO: реализовать регистрацию бота в сессии
        return Figure.ZERO;
    }

    @Override
    public StateOfSession getCurrentStateOfSession(UUID sessionId) {
        // TODO: реализовать получение текущего состояния сессии
        return null;
    }

    @Override
    public StateOfSession getStateOfSessionByTurn(UUID sessionId, Integer turn) {
        // TODO: реализовать получение состояния сессии по ее UUID в момент хода turn
        return null;
    }

    @Override
    public List<StateOfSession> getSessionsByIsActive(Boolean isActive) {
        // TODO: реализовать получение сессий по активности
        return null;
    }
}
