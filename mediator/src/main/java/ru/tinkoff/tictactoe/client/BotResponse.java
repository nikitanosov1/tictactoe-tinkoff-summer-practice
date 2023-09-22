package ru.tinkoff.tictactoe.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BotResponse {
    @JsonProperty(value = "game_field")
    private String gameField;
}
