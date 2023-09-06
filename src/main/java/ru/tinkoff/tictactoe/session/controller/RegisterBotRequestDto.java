package ru.tinkoff.tictactoe.session.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.net.InetAddress;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
class RegisterBotRequestDto {
    @JsonProperty(value = "bot_ip")
    private InetAddress botIp;

    @JsonProperty(value = "bot_port")
    private Integer botPort;
}
