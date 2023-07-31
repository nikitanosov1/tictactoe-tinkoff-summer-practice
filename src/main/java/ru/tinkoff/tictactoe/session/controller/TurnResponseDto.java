package ru.tinkoff.tictactoe.session.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TurnResponseDto {
    @JsonProperty(value = "id")
    private UUID id;

    @JsonProperty(value = "turn")
    private Integer turn;

    @JsonProperty(value = "game_field")
    private String gameField;

    @JsonProperty(value = "created_at")
    private Date createdAt;
}
