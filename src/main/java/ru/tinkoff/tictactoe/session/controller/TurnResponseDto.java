package ru.tinkoff.tictactoe.session.controller;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;

@Builder
@JsonNaming(SnakeCaseStrategy.class)
public record TurnResponseDto(
    UUID id,
    Integer turn,
    String gameField,
    Date createdAt
) {}
