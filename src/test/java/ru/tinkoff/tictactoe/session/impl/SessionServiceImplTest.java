package ru.tinkoff.tictactoe.session.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.model.Session;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {
    @Mock
    private SessionRepository sessionRepositoryMock;

    @InjectMocks
    private SessionServiceImpl sessionService;

    @DisplayName("JUnit test for createSession method")
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
}