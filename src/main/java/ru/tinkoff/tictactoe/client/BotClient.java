package ru.tinkoff.tictactoe.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@FeignClient(name = "botClient", url = "${botClient.ribbon.listOfServers}")
public interface BotClient {

    @PostMapping("/bot/turn")
    BotResponse makeTurn(URI baseUrl, @RequestBody BotRequest botRequest);
}

