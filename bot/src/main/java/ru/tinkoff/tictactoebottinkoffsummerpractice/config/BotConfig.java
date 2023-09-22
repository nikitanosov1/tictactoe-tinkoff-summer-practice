package ru.tinkoff.tictactoebottinkoffsummerpractice.config;

import java.util.UUID;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config")
public record BotConfig(
    String mediatorAddress,
    String botUrl,
    String botId,
    UUID sessionUUID,
    int gameFieldSize
) {}
