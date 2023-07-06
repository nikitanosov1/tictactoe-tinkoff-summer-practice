package ru.tinkoff.tictactoe.session.controller;

import lombok.*;
import ru.tinkoff.tictactoe.session.Figure;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
class RegisterBotResponseDto {
    private Figure figure;
}
