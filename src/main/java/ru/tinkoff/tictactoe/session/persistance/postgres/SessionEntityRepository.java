package ru.tinkoff.tictactoe.session.persistance.postgres;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionEntityRepository extends JpaRepository<SessionEntity, UUID> {

    @Modifying
    @Query("update SessionEntity s set s.attackingBotUrl = :url, s.attackingBotId = :id "
        + "where s.id = :sessionId")
    void updateSessionEntitySetAttackingBot(
        @Param("sessionId") UUID sessionId,
        @Param("url") String url,
        @Param("id") String id
    );

    @Modifying
    @Query("update SessionEntity s set s.defendingBotUrl = :url, s.defendingBotId = :id "
        + "where s.id = :sessionId")
    void updateSessionEntitySetSecondBot(
        @Param("sessionId") UUID sessionId,
        @Param("url") String url,
        @Param("id") String id
    );

    @Modifying
    @Query("update SessionEntity s set s.isActive = false , s.winBot = :winBot where s.id = :sessionId")
    void finishSession(
        @Param("sessionId") UUID sessionId,
        @Param("winBot") String winBot
    );

    @Query("select s from SessionEntity s join fetch s.turnEntities where s.id = :sessionId")
    Optional<SessionEntity> findByIdFetchTurns(UUID sessionId);

    List<SessionEntity> findAllByIsActive(Boolean isActive);
}
