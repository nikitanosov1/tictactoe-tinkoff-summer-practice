package ru.tinkoff.tictactoe.session.persistance.postgres;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionEntityRepository extends JpaRepository<SessionEntity, UUID> {

    @Query("select s from SessionEntity s join fetch s.turnEntities where s.id = :sessionId")
    Optional<SessionEntity> findByIdFetchTurns(UUID sessionId);

}
