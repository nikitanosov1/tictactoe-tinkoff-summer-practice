package ru.tinkoff.tictactoebottinkoffsummerpractice.bot.controller;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(SnakeCaseStrategy.class)
record GameFieldRequestDto(
    String gameField
) {}
