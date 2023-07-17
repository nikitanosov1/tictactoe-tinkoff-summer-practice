package ru.tinkoff.tictactoe.session.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.tictactoe.session.*;
import ru.tinkoff.tictactoe.session.exception.SessionIsAlreadyFullException;
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
        return sessionRepository.createSession();
    }

    @Transactional
    @Override
    public Figure registerBotInSession(UUID sessionId, UUID botId) {
        Session session = sessionRepository.findBySessionId(sessionId);
        if (session.getFirstBotId() == null) {
            sessionRepository.setFirstBotId(session.getId(), botId);
            return Figure.CROSS;
        }
        if (session.getSecondBotId() != null) {
            throw new SessionIsAlreadyFullException();
        }
        sessionRepository.setSecondBotId(session.getId(), botId);
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
