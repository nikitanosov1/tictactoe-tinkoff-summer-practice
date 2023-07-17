package ru.tinkoff.tictactoe.session.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.exception.SessionNotFoundException;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntityRepository;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {
    private final SessionEntityRepository sessionEntityRepository;
    private final SessionEntityMapper sessionEntityMapper;

    @Override
    public Session createSession() {
        SessionEntity sessionEntity = SessionEntity.builder()
                .isActive(false)
                .build();
        return sessionEntityMapper.toSession(sessionEntityRepository.save(sessionEntity));
    }

    @Override
    public Session findBySessionId(UUID sessionId) {
        return sessionEntityRepository.findById(sessionId)
                .map(sessionEntityMapper::toSession)
                .orElseThrow(SessionNotFoundException::new);
    }

    @Override
    public void setFirstBotId(UUID sessionId, UUID botId) {
        sessionEntityRepository.updateSessionEntitySetFirstBotId(sessionId, botId);
    }

    @Override
    public void setSecondBotId(UUID sessionId, UUID botId) {
        sessionEntityRepository.updateSessionEntitySetSecondBotId(sessionId, botId);
    }
}
