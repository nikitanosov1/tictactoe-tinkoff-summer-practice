package ru.tinkoff.tictactoe.session.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
class StateOfSessionResponseDto {
    @JsonProperty(value = "game_field")
    private String gameField;

    @JsonProperty(value = "turn_time")
    private Date turnTime;

    @JsonProperty(value = "turn")
    private Integer turn;
}
