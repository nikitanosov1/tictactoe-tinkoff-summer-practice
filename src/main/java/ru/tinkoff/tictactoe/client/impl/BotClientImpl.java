package ru.tinkoff.tictactoe.client.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.tinkoff.tictactoe.client.BotClient;
import ru.tinkoff.tictactoe.client.BotRequest;
import ru.tinkoff.tictactoe.client.BotResponse;
import ru.tinkoff.tictactoe.client.exception.BotResponseStatusCodeNotOkException;

import java.net.URI;

@RequiredArgsConstructor
@Component
@Slf4j
public class BotClientImpl implements BotClient {
    private final RestTemplate restTemplate;

    @Value("${bots.url-path}")
    private String urlPath;

    @Override
    public BotResponse makeTurn(URI baseUrl, BotRequest botRequest) {
        String url = String.format("%s%s", baseUrl, urlPath);
        HttpEntity<BotRequest> request = new HttpEntity<>(botRequest);
        log.info("makeTurn to {}:", url);
        ResponseEntity<BotResponse> response = restTemplate
                .exchange(url, HttpMethod.POST, request, BotResponse.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new BotResponseStatusCodeNotOkException();
        }
        BotResponse botResponse = response.getBody();

        log.info("makeTurn from {}: gameField from response {}", url, botResponse.getGameField());

        return botResponse;
    }
}
