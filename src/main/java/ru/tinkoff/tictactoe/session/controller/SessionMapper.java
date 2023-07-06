package ru.tinkoff.tictactoe.session.controller;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.tictactoe.session.Session;
import ru.tinkoff.tictactoe.session.StateOfSession;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(source = "id", target = "sessionId")
    CreateSessionResponseDto toCreateSessionResponseDto(Session session);

    List<FullStateOfSessionResponseDto> toListFullStateOfSessionResponseDto(List<StateOfSession> stateOfSessions);

    @Mapping(source = "updatedAt", target = "lastTurnTime")
    StateOfSessionResponseDto toStateOfSessionResponseDto(StateOfSession stateOfSession);
}
