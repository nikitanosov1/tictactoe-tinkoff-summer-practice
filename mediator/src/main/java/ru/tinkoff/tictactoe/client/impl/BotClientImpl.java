package ru.tinkoff.tictactoe.client.impl;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.tinkoff.tictactoe.client.BotClient;
import ru.tinkoff.tictactoe.client.BotRequest;
import ru.tinkoff.tictactoe.client.BotResponse;
import ru.tinkoff.tictactoe.client.exception.BotResponseStatusCodeNotOkException;

@RequiredArgsConstructor
@Component
@Slf4j
public class BotClientImpl implements BotClient {

    private final RestTemplate restTemplate;

    private static final String urlPath = "/bot/turn";

    @Override
    public BotResponse makeTurn(URI baseUrl, BotRequest botRequest) {
        String url = String.format("%s%s", baseUrl, urlPath);
        HttpEntity<BotRequest> request = new HttpEntity<>(botRequest);
        log.trace("makeTurn to {}:", url);
        ResponseEntity<BotResponse> response = restTemplate.exchange(
            url,
            POST,
            request,
            BotResponse.class
        );
        if (response.getStatusCode() != OK) {
            throw new BotResponseStatusCodeNotOkException();
        }
        BotResponse botResponse = response.getBody();
        log.debug("makeTurn from {}: gameField from response {}", url, botResponse.getGameField());

        return botResponse;
    }
}
