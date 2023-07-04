package ru.tinkoff.tictactoe.session.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.Session;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.SessionService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public Session createSession() {
        Session session = Session.builder()
                .isActive(false)
                .totalTurns(0)
                .build();
        return sessionRepository.save(session);
    }

    @Override
    public Figure registerBotInSession(UUID sessionId, UUID botId) {
        // TODO: реализовать регистрацию бота в сессии
        return Figure.ZERO;
    }
}
