package ru.tinkoff.tictactoe.session.impl;

import org.mapstruct.Mapper;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;

@Mapper(componentModel = "spring")
interface SessionEntityMapper {
    SessionEntity toSessionEntity(Session session);

    Session toSession(SessionEntity sessionEntity);
}
