package ru.tinkoff.tictactoe.session.persistance.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.net.InetAddress;
import java.util.List;
import java.util.UUID;

@Repository
public interface SessionEntityRepository extends JpaRepository<SessionEntity, UUID> {
    @Modifying
    @Query("update SessionEntity s set s.firstBotIp = :firstBotIp, s.firstBotPort = :firstBotPort where s.id = :sessionId")
    void updateSessionEntitySetFirstBotIpAndFirstBotPort(@Param("sessionId") UUID sessionId,
                                                         @Param("firstBotIp") InetAddress firstBotIp,
                                                         @Param("firstBotPort") Integer firstBotPort);

    @Modifying
    @Query("update SessionEntity s set s.secondBotIp = :secondBotIp, s.secondBotPort = :secondBotPort where s.id = :sessionId")
    void updateSessionEntitySetSecondBotIpAndSecondBotPort(@Param("sessionId") UUID sessionId,
                                                           @Param("secondBotIp") InetAddress secondBotIp,
                                                           @Param("secondBotPort") Integer secondBotPort);

    List<SessionEntity> findAllByIsActive(Boolean isActive);
}
