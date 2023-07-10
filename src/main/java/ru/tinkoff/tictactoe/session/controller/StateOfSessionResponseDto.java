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

    @JsonProperty(value = "last_turn_time")
    private Date lastTurnTime;

    @JsonProperty(value = "turn")
    private Integer turn;
}
