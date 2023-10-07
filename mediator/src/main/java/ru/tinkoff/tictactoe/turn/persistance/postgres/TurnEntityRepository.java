package ru.tinkoff.tictactoe.turn.persistance.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TurnEntityRepository extends JpaRepository<TurnEntity, UUID> {
    Optional<TurnEntity> findBySessionEntityAndTurn(SessionEntity sessionEntity, Integer turn);
}
