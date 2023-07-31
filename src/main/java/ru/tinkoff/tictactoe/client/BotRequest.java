package ru.tinkoff.tictactoe.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class BotRequest {
    @JsonProperty(value = "game_field")
    private String gameField;
}
