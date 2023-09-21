package ru.tinkoff.tictactoe.session.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.GameService;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.SessionService;
import ru.tinkoff.tictactoe.session.exception.SessionIsAlreadyFullException;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.model.SessionWithAllTurns;
import ru.tinkoff.tictactoe.session.model.SessionWithLastTurn;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final GameService gameService;

    @Transactional
    @Override
    public Session createSession() {
        return sessionRepository.createSession();
    }

    @Transactional
    @Override
    public Figure registerBotInSession(UUID sessionId, String url, String botId) throws InterruptedException {
        Session session = sessionRepository.findBySessionId(sessionId);
        if (session.attackingBotUrl() == null) {
            sessionRepository.setAttackingBot(sessionId, url, botId);
            return GameService.ATTACKING_BOT_FIGURE;
        }
        if (session.defendingBotUrl() != null) {
            throw new SessionIsAlreadyFullException();
        }
        sessionRepository.setDefendingBot(sessionId, url, botId);
        gameService.startGame(session.id());
        return GameService.DEFENDING_BOT_FIGURE;
    }

    @Override
    public SessionWithLastTurn getSession(UUID sessionId) {
        return sessionRepository.findFetchLastTurnBySessionId(sessionId);
    }

    @Override
    public List<Session> getSessionsByIsActive(Boolean isActive) {
        log.info("SessionServiceImpl: getSessionsByIsActive stared with isActive = {}", isActive);
        if (isActive == null) {
            return sessionRepository.findAllSessions();
        }
        return sessionRepository.findSessionsByIsActive(isActive);
    }
}
