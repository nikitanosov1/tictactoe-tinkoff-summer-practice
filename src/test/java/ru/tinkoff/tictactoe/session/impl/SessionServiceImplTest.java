package ru.tinkoff.tictactoe.session.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {
    @Mock
    private SessionRepository sessionRepositoryMock;

    @Spy
    private final SessionEntityMapper sessionEntityMapper = Mappers.getMapper(SessionEntityMapper.class);

    @InjectMocks
    private SessionServiceImpl sessionService;

    @DisplayName("JUnit test for createSession method")
    @Test
    void whenCreateSession_thenReturnSession() {
        SessionEntity sessionEntity = SessionEntity.builder()
                .id(UUID.randomUUID())
                .isActive(false)
                .createdAt(new Date())
                .updatedAt(new Date())
                .turnEntities(List.of())
                .build();
        when(sessionRepositoryMock.createSessionEntity()).thenReturn(sessionEntity);
        Session session = sessionService.createSession();
        verify(sessionRepositoryMock, times(1)).createSessionEntity();
        assertThat(session).isNotNull();
        assertThat(session.getId()).isEqualTo(sessionEntity.getId());
        assertThat(session.getIsActive()).isEqualTo(sessionEntity.getIsActive());
        assertThat(session.getCreatedAt()).isEqualTo(sessionEntity.getCreatedAt());
        assertThat(session.getUpdatedAt()).isEqualTo(sessionEntity.getUpdatedAt());
    }
}