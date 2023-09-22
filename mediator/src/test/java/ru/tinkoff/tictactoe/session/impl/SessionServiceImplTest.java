package ru.tinkoff.tictactoe.session.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.GameService;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.exception.SessionIsAlreadyFullException;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.model.SessionStatus;

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
            .status(SessionStatus.NEW)
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();
        when(sessionRepositoryMock.createSession()).thenReturn(session);
        Session returnedSession = sessionService.createSession();
        verify(sessionRepositoryMock, times(1)).createSession();
        assertThat(returnedSession)
            .isNotNull()
            .isEqualTo(session);
    }

    @Test
    void givenBotIdAndSessionId_whenForTheFirstTimeRegisterBotInSession_thenReturnCrossFigure()
        throws InterruptedException {
        UUID sessionId = UUID.randomUUID();
        final var firstBotId = "attacking";
        final var firstBotUrl = String.format("http://%s:%d", "1.1.1.1", 1111);
        Session session = Session.builder()
            .id(sessionId)
            .status(SessionStatus.NEW)
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();
        when(sessionRepositoryMock.findBySessionId(sessionId)).thenReturn(session);
        Figure figure = sessionService.registerBotInSession(
            sessionId,
            firstBotUrl,
            firstBotId
        );
        assertThat(figure)
            .isNotNull()
            .isEqualTo(Figure.CROSS);
    }

    @Test
    void givenBotIdAndSessionId_whenForTheSecondTimeRegisterBotInSession_thenReturnZeroFigure()
        throws InterruptedException {
        UUID sessionId = UUID.randomUUID();
        final var firstBotId = "attacking";
        final var firstBotUrl = String.format("http://%s:%d", "1.1.1.1", 1111);
        final var secondBotUrl = String.format("http://%s:%d", "2.2.2.2", 2222);
        final var secondBotId = "defending";
        Session session = Session.builder()
            .id(sessionId)
            .status(SessionStatus.NEW)
            .attackingBotUrl(firstBotUrl)
            .attackingBotId(firstBotId)
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();
        when(sessionRepositoryMock.findBySessionId(sessionId)).thenReturn(session);
        Figure figure = sessionService.registerBotInSession(
            sessionId,
            secondBotUrl,
            secondBotId
        );
        assertThat(figure)
            .isNotNull()
            .isEqualTo(Figure.ZERO);
        verify(gameServiceMock, only()).startGame(sessionId);
    }

    @Test
    void givenBotIdAndSessionId_whenForTheThirdTimeRegisterBotInSession_thenThrowApiException() {
        UUID sessionId = UUID.randomUUID();
        final var firstBotId = "attacking";
        final var firstBotUrl = String.format("http://%s:%d", "1.1.1.1", 1111);
        final var secondBotUrl = String.format("http://%s:%d", "2.2.2.2", 2222);
        final var secondBotId = "defending";
        Session session = Session.builder()
            .id(sessionId)
            .status(SessionStatus.ONGOING)
            .attackingBotId(firstBotId)
            .attackingBotUrl(firstBotUrl)
            .defendingBotId(secondBotId)
            .defendingBotUrl(secondBotUrl)
            .createdAt(new Date())
            .updatedAt(new Date())
            .build();
        when(sessionRepositoryMock.findBySessionId(sessionId)).thenReturn(session);
        assertThrows(SessionIsAlreadyFullException.class, () ->
            sessionService.registerBotInSession(sessionId, secondBotUrl, secondBotId)
        );
    }
}