package ru.tinkoff.tictactoe.session.controller;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;

@Builder
@JsonNaming(SnakeCaseStrategy.class)
record SessionLastTurnResponseDto(
    UUID id,
    String attackingBotUrl,
    String attackingBotId,
    String defendingBotUrl,
    String defendingBotId,
    TurnResponseDto lastTurn,
    String status,
    Date updatedAt,
    Date createdAt
) {}
