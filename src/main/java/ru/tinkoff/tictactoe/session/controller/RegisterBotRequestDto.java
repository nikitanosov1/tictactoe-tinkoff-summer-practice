package ru.tinkoff.tictactoe.session.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
class RegisterBotRequestDto {
    @JsonProperty(value = "bot_id")
    private UUID botId;
}
