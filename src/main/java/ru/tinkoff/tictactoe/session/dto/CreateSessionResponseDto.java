package ru.tinkoff.tictactoe.session.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateSessionResponseDto {
    @JsonProperty(value = "session_id")
    private UUID sessionId;
}
