package ru.tinkoff.tictactoe.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "botClient", url = "${botClient.ribbon.listOfServers}")
public interface BotClient {

    @PostMapping("/bot/turn/{botId}")
    BotResponse makeTurn(@PathVariable("botId") UUID botId, @RequestBody BotRequest botRequest);
}

