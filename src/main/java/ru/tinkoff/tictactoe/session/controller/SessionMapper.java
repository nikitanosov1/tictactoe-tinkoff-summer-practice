package ru.tinkoff.tictactoe.session.controller;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.model.SessionWithAllTurns;
import ru.tinkoff.tictactoe.session.model.SessionWithLastTurn;
import ru.tinkoff.tictactoe.turn.model.Turn;

@Mapper(componentModel = "spring")
interface SessionMapper {

    @Mapping(source = "id", target = "sessionId")
    CreateSessionResponseDto toCreateSessionResponseDto(Session session);

    List<SessionResponseDto> toListSessionResponseDto(List<Session> sessions);

    SessionLastTurnResponseDto toSessionResponseDto(SessionWithLastTurn session);

    TurnResponseDto toTurnResponseDto(Turn turn);
}
