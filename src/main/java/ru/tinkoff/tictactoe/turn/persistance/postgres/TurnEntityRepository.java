package ru.tinkoff.tictactoe.turn.persistance.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TurnEntityRepository extends JpaRepository<TurnEntity, UUID> {
}
