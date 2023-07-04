package ru.tinkoff.tictactoe.session.dto;

import lombok.*;
import ru.tinkoff.tictactoe.session.Figure;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterBotResponseDto {
    private Figure figure;
}
