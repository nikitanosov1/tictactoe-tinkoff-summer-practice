package ru.tinkoff.tictactoe.session.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.GameService;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.exception.SessionIsAlreadyFullException;
import ru.tinkoff.tictactoe.session.model.Session;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {
    @Mock
    private SessionRepository sessionRepositoryMock;

    @Mock
    private GameService gameServiceMock;

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
                .turns(List.of())
                .build();
        when(sessionRepositoryMock.createSession()).thenReturn(session);
        Session returnedSession = sessionService.createSession();
        verify(sessionRepositoryMock, times(1)).createSession();
        assertThat(returnedSession)
                .isNotNull()
                .isEqualTo(session);
    }

    @Test
    void givenBotIdAndSessionId_whenForTheFirstTimeRegisterBotInSession_thenReturnCrossFigure() throws UnknownHostException {
        UUID sessionId = UUID.randomUUID();
        String firstBotIp = "1.1.1.1";
        int firstBotPort = 1111;
        Session session = Session.builder()
                .id(sessionId)
                .isActive(false)
                .createdAt(new Date())
                .updatedAt(new Date())
                .turns(List.of())
                .build();
        when(sessionRepositoryMock.findBySessionId(sessionId)).thenReturn(session);
        Figure figure = sessionService.registerBotInSession(sessionId, InetAddress.getByName(firstBotIp), firstBotPort);
        verify(sessionRepositoryMock, times(1)).findBySessionId(sessionId);
        assertThat(figure)
                .isNotNull()
                .isEqualTo(Figure.CROSS);
    }

    @Test
    void givenBotIdAndSessionId_whenForTheSecondTimeRegisterBotInSession_thenReturnZeroFigure() throws UnknownHostException {
        UUID sessionId = UUID.randomUUID();
        String firstBotIp = "1.1.1.1";
        int firstBotPort = 1111;
        String secondBotIp = "2.2.2.2";
        int secondBotPort = 2222;
        Session session = Session.builder()
                .id(sessionId)
                .isActive(false)
                .firstBotIp(InetAddress.getByName(firstBotIp))
                .firstBotPort(firstBotPort)
                .createdAt(new Date())
                .updatedAt(new Date())
                .turns(List.of())
                .build();
        when(sessionRepositoryMock.findBySessionId(sessionId)).thenReturn(session);
        when(gameServiceMock.startGame(sessionId)).thenReturn(null);
        Figure figure = sessionService.registerBotInSession(sessionId, InetAddress.getByName(secondBotIp), secondBotPort);
        verify(sessionRepositoryMock, times(1)).findBySessionId(sessionId);
        assertThat(figure)
                .isNotNull()
                .isEqualTo(Figure.ZERO);
    }

    @Test
    void givenBotIdAndSessionId_whenForTheThirdTimeRegisterBotInSession_thenThrowApiException() throws UnknownHostException {
        UUID sessionId = UUID.randomUUID();
        String firstBotIp = "1.1.1.1";
        int firstBotPort = 1111;
        String secondBotIp = "2.2.2.2";
        int secondBotPort = 2222;
        Session session = Session.builder()
                .id(sessionId)
                .isActive(false)
                .firstBotIp(InetAddress.getByName(firstBotIp))
                .firstBotPort(firstBotPort)
                .secondBotIp(InetAddress.getByName(secondBotIp))
                .secondBotPort(secondBotPort)
                .createdAt(new Date())
                .updatedAt(new Date())
                .turns(List.of())
                .build();
        when(sessionRepositoryMock.findBySessionId(sessionId)).thenReturn(session);
        assertThrows(SessionIsAlreadyFullException.class, () -> {
            sessionService.registerBotInSession(sessionId, InetAddress.getByName(secondBotIp), secondBotPort);
        });
    }
}