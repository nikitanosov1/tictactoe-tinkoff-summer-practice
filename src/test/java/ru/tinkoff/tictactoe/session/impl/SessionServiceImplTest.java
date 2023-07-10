package ru.tinkoff.tictactoe.session.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.exception.ApiException;
import ru.tinkoff.tictactoe.session.model.Session;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {
    @Mock
    private SessionRepository sessionRepositoryMock;

    @InjectMocks
    private SessionServiceImpl sessionService;

    //@DisplayName("JUnit test for createSession method")
    @Test
    void whenCreateSession_thenReturnSession() {
        Session session = Session.builder()
                .id(UUID.randomUUID())
                .isActive(false)
                .createdAt(new Date())
                .updatedAt(new Date())
                .turnEntities(List.of())
                .build();
        when(sessionRepositoryMock.createSession()).thenReturn(session);
        Session returnedSession = sessionService.createSession();
        verify(sessionRepositoryMock, times(1)).createSession();
        assertThat(returnedSession)
                .isNotNull()
                .isEqualTo(session);
    }

    @Test
    void givenBotIdAndSessionId_whenForTheFirstTimeRegisterBotInSession_thenReturnCrossFigure() {
        UUID sessionId = UUID.randomUUID();
        UUID firstBotId = UUID.randomUUID();
        Session session = Session.builder()
                .id(sessionId)
                .isActive(false)
                .createdAt(new Date())
                .updatedAt(new Date())
                .turnEntities(List.of())
                .build();
        when(sessionRepositoryMock.findBySessionId(sessionId)).thenReturn(session);
        Figure figure = sessionService.registerBotInSession(sessionId, firstBotId);
        verify(sessionRepositoryMock, times(1)).findBySessionId(sessionId);
        assertThat(figure)
                .isNotNull()
                .isEqualTo(Figure.CROSS);
    }

    @Test
    void givenBotIdAndSessionId_whenForTheSecondTimeRegisterBotInSession_thenReturnZeroFigure() {
        UUID sessionId = UUID.randomUUID();
        UUID firstBotId = UUID.randomUUID();
        UUID secondBotId = UUID.randomUUID();
        Session session = Session.builder()
                .id(sessionId)
                .isActive(false)
                .firstBotId(firstBotId)
                .createdAt(new Date())
                .updatedAt(new Date())
                .turnEntities(List.of())
                .build();
        when(sessionRepositoryMock.findBySessionId(sessionId)).thenReturn(session);
        Figure figure = sessionService.registerBotInSession(sessionId, secondBotId);
        verify(sessionRepositoryMock, times(1)).findBySessionId(sessionId);
        assertThat(figure)
                .isNotNull()
                .isEqualTo(Figure.ZERO);
    }

    @Test
    void givenBotIdAndSessionId_whenForTheThirdTimeRegisterBotInSession_thenThrowApiException() {
        UUID sessionId = UUID.randomUUID();
        UUID firstBotId = UUID.randomUUID();
        UUID secondBotId = UUID.randomUUID();
        Session session = Session.builder()
                .id(sessionId)
                .isActive(false)
                .firstBotId(firstBotId)
                .secondBotId(secondBotId)
                .createdAt(new Date())
                .updatedAt(new Date())
                .turnEntities(List.of())
                .build();
        when(sessionRepositoryMock.findBySessionId(sessionId)).thenReturn(session);
        assertThrows(ApiException.class, () -> {
            sessionService.registerBotInSession(sessionId, secondBotId);
        });
    }
}