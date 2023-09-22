package ru.tinkoff.tictactoe.turn.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;
import ru.tinkoff.tictactoe.turn.TurnRepository;
import ru.tinkoff.tictactoe.turn.exception.TurnNotFoundException;
import ru.tinkoff.tictactoe.turn.model.Turn;
import ru.tinkoff.tictactoe.turn.persistance.postgres.TurnEntityRepository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TurnRepositoryImpl implements TurnRepository {
    private final TurnEntityRepository turnEntityRepository;
    private final TurnEntityMapper turnEntityMapper;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Turn findTurnBySessionIdAndTurn(UUID sessionId, Integer turn) {
        return turnEntityRepository
                .findBySessionEntityAndTurn(entityManager.getReference(SessionEntity.class, sessionId), turn)
                .map(turnEntityMapper::toTurn)
                .orElseThrow(TurnNotFoundException::new);
    }
}
