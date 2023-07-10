package ru.tinkoff.tictactoe.session.controller;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.turn.model.StateOfSession;

import java.util.List;

@Mapper(componentModel = "spring")
interface SessionMapper {

    @Mapping(source = "id", target = "sessionId")
    CreateSessionResponseDto toCreateSessionResponseDto(Session session);

    List<FullStateOfSessionResponseDto> toListFullStateOfSessionResponseDto(List<StateOfSession> stateOfSessions);

    @Mapping(source = "updatedAt", target = "lastTurnTime")
    StateOfSessionResponseDto toStateOfSessionResponseDto(StateOfSession stateOfSession);
}
