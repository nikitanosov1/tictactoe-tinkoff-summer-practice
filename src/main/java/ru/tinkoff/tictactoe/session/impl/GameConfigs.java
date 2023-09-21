package ru.tinkoff.tictactoe.session.impl;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config")
public record GameConfigs(
    Duration sleepDuration
) {}
