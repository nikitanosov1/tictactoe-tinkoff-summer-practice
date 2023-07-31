package ru.tinkoff.tictactoe.session.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;

import java.util.List;

@Mapper(componentModel = "spring")
interface SessionEntityMapper {
    @Mapping(source = "turns", target = "turnEntities")
    SessionEntity toSessionEntity(Session session);

    @Mapping(source = "turnEntities", target = "turns")
    Session toSession(SessionEntity sessionEntity);

    List<Session> toListOfSession(List<SessionEntity> sessionEntities);
}
