package ru.tinkoff.tictactoe.session.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;

@Mapper(componentModel = "spring")
interface SessionEntityMapper {
    @Mapping(source = "turns", target = "turnEntities")
    SessionEntity toSessionEntity(Session session);

    @Mapping(source = "turnEntities", target = "turns")
    Session toSession(SessionEntity sessionEntity);
}
