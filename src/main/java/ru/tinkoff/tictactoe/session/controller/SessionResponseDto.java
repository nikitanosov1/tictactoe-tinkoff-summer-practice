package ru.tinkoff.tictactoe.session.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
class SessionResponseDto {
    @JsonProperty(value = "session_id")
    private UUID id;

    @JsonProperty(value = "first_bot_ip")
    private InetAddress firstBotIp;

    @JsonProperty(value = "first_bot_port")
    private Integer firstBotPort;

    @JsonProperty(value = "second_bot_ip")
    private InetAddress secondBotIp;

    @JsonProperty(value = "second_bot_port")
    private Integer secondBotPort;

    @JsonProperty(value = "turns")
    private List<TurnResponseDto> turns;

    @JsonProperty(value = "is_active")
    private Boolean isActive;

    @JsonProperty(value = "updated_at")
    private Date updatedAt;

    @JsonProperty(value = "created_at")
    private Date createdAt;
}
