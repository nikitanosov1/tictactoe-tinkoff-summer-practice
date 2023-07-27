package ru.tinkoff.tictactoe.session.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.exception.SessionNotFoundException;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntityRepository;
import ru.tinkoff.tictactoe.turn.model.Turn;
import ru.tinkoff.tictactoe.turn.persistance.postgres.TurnEntity;
import ru.tinkoff.tictactoe.turn.persistance.postgres.TurnEntityRepository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {
    private final SessionEntityRepository sessionEntityRepository;
    private final TurnEntityRepository turnEntityRepository;
    private final SessionEntityMapper sessionEntityMapper;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Session createSession() {
        SessionEntity sessionEntity = SessionEntity.builder()
                .isActive(false)
                .build();
        TurnEntity turnEntity = TurnEntity.builder()
                .turn(0)
                .sessionEntity(sessionEntity)
                .gameField("_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________")
                .build();
        sessionEntity.setTurnEntities(List.of(turnEntity));
        return sessionEntityMapper.toSession(sessionEntityRepository.save(sessionEntity));
    }

    @Transactional
    @Override
    public Session findBySessionId(UUID sessionId) {
        return sessionEntityRepository.findById(sessionId)
                .map(sessionEntityMapper::toSession)
                .orElseThrow(SessionNotFoundException::new);
    }

    @Transactional
    @Override
    public void setFirstBotId(UUID sessionId, UUID botId) {
        sessionEntityRepository.updateSessionEntitySetFirstBotId(sessionId, botId);
    }

    @Transactional
    @Override
    public void setSecondBotId(UUID sessionId, UUID botId) {
        sessionEntityRepository.updateSessionEntitySetSecondBotId(sessionId, botId);
    }

    @Override
    public void addTurnToSession(UUID sessionId, Turn turn) {
        SessionEntity sessionEntity = entityManager.find(SessionEntity.class, sessionId);
        TurnEntity turnEntity = TurnEntity.builder()
                .turn(turn.getTurn())
                .gameField(turn.getGameField())
                .sessionEntity(sessionEntity)
                .build();
        turnEntityRepository.save(turnEntity);
    }
}
