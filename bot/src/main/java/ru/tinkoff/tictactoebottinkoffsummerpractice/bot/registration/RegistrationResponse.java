package ru.tinkoff.tictactoebottinkoffsummerpractice.bot.registration;

import lombok.Builder;
import ru.tinkoff.tictactoebottinkoffsummerpractice.bot.Figure;

@Builder
public record RegistrationResponse(
    Figure figure
) {}
