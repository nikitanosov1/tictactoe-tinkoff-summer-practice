package ru.tinkoff.tictactoe.session.impl;

import static ru.tinkoff.tictactoe.session.GameService.ZERO_TURN_GAME_FIELD;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.exception.SessionNotFoundException;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.model.SessionStatus;
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
            .status(SessionStatus.NEW.name())
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
    @Transactional
    public void startSession(UUID sessionId) {
        final var sessionReference = sessionEntityRepository.getReferenceById(sessionId);
        sessionReference.setStatus(SessionStatus.ONGOING.name());
        sessionEntityRepository.save(sessionReference);
    }

    @Override
    @Transactional
    public void finishSession(UUID sessionId, String winBot) {
        final var sessionReference = sessionEntityRepository.getReferenceById(sessionId);
        sessionReference.setStatus(SessionStatus.FINISHED.name());
        sessionReference.setWinBot(winBot);
        sessionEntityRepository.save(sessionReference);
    }

    @Override
    public Session findBySessionId(UUID sessionId) {
        final var sessionEntity = sessionEntityRepository.findById(sessionId)
            .orElseThrow(SessionNotFoundException::new);
        return sessionEntityMapper.toSession(sessionEntity);
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
        final var sessionReference = sessionEntityRepository.getReferenceById(sessionId);
        sessionReference.setAttackingBotUrl(url);
        sessionReference.setAttackingBotId(id);
        sessionEntityRepository.save(sessionReference);
    }

    @Transactional
    @Override
    public void setDefendingBot(UUID sessionId, String url, String id) {
        final var sessionReference = sessionEntityRepository.getReferenceById(sessionId);
        sessionReference.setDefendingBotUrl(url);
        sessionReference.setDefendingBotId(id);
        sessionEntityRepository.save(sessionReference);
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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Session> cq = cb.createQuery(Session.class);

        Root<Session> sessionRoot = cq.from(Session.class);
        List<Predicate> predicates = new ArrayList<>();

        if (isActive != null) {
            if (isActive) {
                predicates.add(cb.equal(sessionRoot.get("status"), SessionStatus.ONGOING.name()));
            } else {
                predicates.add(cb.notEqual(sessionRoot.get("status"), SessionStatus.ONGOING.name()));
            }
        }
        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}
