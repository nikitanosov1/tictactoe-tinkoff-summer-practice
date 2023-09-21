package ru.tinkoff.tictactoe.session.impl;

import static ru.tinkoff.tictactoe.session.GameService.ZERO_TURN_GAME_FIELD;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.exception.SessionNotFoundException;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.model.SessionWithAllTurns;
import ru.tinkoff.tictactoe.session.model.SessionWithLastTurn;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntityRepository;
import ru.tinkoff.tictactoe.turn.model.Turn;
import ru.tinkoff.tictactoe.turn.persistance.postgres.TurnEntity;
import ru.tinkoff.tictactoe.turn.persistance.postgres.TurnEntityRepository;

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
            .gameField(ZERO_TURN_GAME_FIELD)
            .build();
        sessionEntity.setTurnEntities(List.of(turnEntity));
        return sessionEntityMapper.toSession(sessionEntityRepository.save(sessionEntity));
    }

    @Override
    public void finishSession(UUID sessionId, String winBot) {
        sessionEntityRepository.finishSession(sessionId, winBot);
    }

    @Override
    public Session findBySessionId(UUID sessionId) {
        return sessionEntityMapper.toSession(findById(sessionId));
    }

    @Override
    public SessionWithAllTurns findFetchTurnsBySessionId(UUID sessionId) {
        return sessionEntityMapper.toAllTurnsSession(findById(sessionId));
    }

    @Override
    public SessionWithLastTurn findFetchLastTurnBySessionId(UUID sessionId) {
        return sessionEntityMapper.toLastTurnSession(findById(sessionId));
    }

    private SessionEntity findById(UUID sessionId) {
        return sessionEntityRepository.findByIdFetchTurns(sessionId)
            .orElseThrow(SessionNotFoundException::new);
    }

    @Transactional
    @Override
    public void setAttackingBot(UUID sessionId, String url, String id) {
        sessionEntityRepository.updateSessionEntitySetAttackingBot(sessionId, url, id);
    }

    @Transactional
    @Override
    public void setDefendingBot(UUID sessionId, String url, String id) {
        sessionEntityRepository.updateSessionEntitySetSecondBot(sessionId, url, id);
    }

    @Override
    public void addTurnToSession(UUID sessionId, Turn turn) {
        TurnEntity turnEntity = TurnEntity.builder()
            .turn(turn.getTurn())
            .gameField(turn.getGameField())
            .sessionEntity(entityManager.getReference(SessionEntity.class, sessionId))
            .build();
        turnEntityRepository.save(turnEntity);
    }

    @Override
    public List<Session> findAllSessions() {
        return sessionEntityMapper.toListOfSession(sessionEntityRepository.findAll());
    }

    @Override
    public List<Session> findSessionsByIsActive(Boolean isActive) {
        return sessionEntityMapper.toListOfSession(sessionEntityRepository.findAllByIsActive(isActive));
    }
}
