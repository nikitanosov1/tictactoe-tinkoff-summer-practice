package ru.tinkoff.tictactoe.session.controller;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.turn.model.Turn;

import java.util.List;

@Mapper(componentModel = "spring")
interface SessionMapper {

    @Mapping(source = "id", target = "sessionId")
    CreateSessionResponseDto toCreateSessionResponseDto(Session session);

    List<SessionResponseDto> toListSessionResponseDto(List<Session> sessions);

    SessionResponseDto toSessionResponseDto(Session session);

    TurnResponseDto toTurnResponseDto(Turn turn);
}
