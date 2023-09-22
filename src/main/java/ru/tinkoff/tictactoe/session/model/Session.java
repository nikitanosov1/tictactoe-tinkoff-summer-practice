package ru.tinkoff.tictactoe.session.model;

import java.util.Date;
import java.util.UUID;
import lombok.Builder;

@Builder
public record Session(
    UUID id,
    String attackingBotUrl,
    String attackingBotId,
    String defendingBotUrl,
    String defendingBotId,
    SessionStatus status,
    Date updatedAt,
    Date createdAt
) {}
