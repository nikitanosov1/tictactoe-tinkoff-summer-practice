package ru.tinkoff.tictactoe.session.persistance.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionEntityRepository extends JpaRepository<SessionEntity, UUID> {
    @Modifying
    @Query("update SessionEntity s set s.firstBotId = :botId where s.id = :sessionId")
    void updateSessionEntitySetFirstBotId(@Param("sessionId") UUID sessionId,
                                          @Param("botId") UUID botId);

    @Modifying
    @Query("update SessionEntity s set s.secondBotId = :botId where s.id = :sessionId")
    void updateSessionEntitySetSecondBotId(@Param("sessionId") UUID sessionId,
                                           @Param("botId") UUID botId);
}
