package ru.tinkoff.tictactoe.session.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntityRepository;

@Repository
@AllArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {
    private final SessionEntityRepository sessionEntityRepository;

    @Override
    public SessionEntity createSessionEntity() {
        SessionEntity sessionEntity = SessionEntity.builder()
                .isActive(false)
                .build();
        return sessionEntityRepository.save(sessionEntity);
    }
}
