//package ru.tinkoff.tictactoe.session.controller;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mapstruct.factory.Mappers;
//import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;
//
//import java.util.UUID;
//
//class SessionMapperTest {
//    private final SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);
//
//    @Test
//    void givenSession_whenMap_thenReturnCreateSessionResponseDto() {
//        //given
//        SessionEntity sessionEntity = SessionEntity.builder()
//                .id(UUID.randomUUID())
//                .firstBotId(UUID.randomUUID())
//                .secondBotId(UUID.randomUUID())
//                .isActive(false)
//                .totalTurns(10)
//                .build();
//
//        //when
//        CreateSessionResponseDto createSessionResponseDto = sessionMapper.toCreateSessionResponseDto(sessionEntity);
//
//        //then
//        Assertions.assertEquals(sessionEntity.getId(), createSessionResponseDto.getSessionId());
//    }
//}