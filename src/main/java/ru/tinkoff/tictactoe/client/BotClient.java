package ru.tinkoff.tictactoe.client;

import java.net.URI;

public interface BotClient {
    BotResponse makeTurn(URI baseUrl, BotRequest botRequest);
}

