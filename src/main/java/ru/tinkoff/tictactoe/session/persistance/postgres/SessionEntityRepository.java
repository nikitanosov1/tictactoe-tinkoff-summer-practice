package ru.tinkoff.tictactoe.session.persistance.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionEntityRepository extends JpaRepository<SessionEntity, UUID> {
}
