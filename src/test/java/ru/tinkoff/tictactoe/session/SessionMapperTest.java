package ru.tinkoff.tictactoe.session;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.tictactoe.session.dto.CreateSessionResponseDto;

import java.util.UUID;

class SessionMapperTest {
    private final SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);

    @Test
    void givenSession_whenMap_thenReturnCreateSessionResponseDto() {
        //given
        Session session = Session.builder()
                .id(UUID.randomUUID())
                .firstBotId(UUID.randomUUID())
                .secondBotId(UUID.randomUUID())
                .isActive(false)
                .totalTurns(10)
                .build();

        //when
        CreateSessionResponseDto createSessionResponseDto = sessionMapper.toCreateSessionResponseDto(session);

        //then
        Assertions.assertEquals(session.getId(), createSessionResponseDto.getSessionId());
    }
}