package ru.tinkoff.tictactoe.session.impl;

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
    
    @Transactional
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
    public Session addTurnToSession(UUID sessionId, Turn turn) {
        TurnEntity turnEntity = TurnEntity.builder()
                .turn(turn.getTurn())
                .gameField(turn.getGameField())
                .build();
        SessionEntity sessionEntity = sessionEntityRepository.findById(sessionId)
                .orElseThrow(RuntimeException::new);
        turnEntity.setSessionEntity(sessionEntity);
        sessionEntity.getTurnEntities().add(turnEntity);
        return sessionEntityMapper.toSession(sessionEntityRepository.save(sessionEntity));
    }
}
