package ru.tinkoff.tictactoe.session;

import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;

public interface SessionRepository {
    SessionEntity createSessionEntity();
}
