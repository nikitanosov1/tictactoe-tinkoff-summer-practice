package ru.tinkoff.tictactoe.session;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.tictactoe.session.dto.CreateSessionResponseDto;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(source = "id", target = "sessionId")
    CreateSessionResponseDto toCreateSessionResponseDto(Session session);
}
