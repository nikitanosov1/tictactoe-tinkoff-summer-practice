package ru.tinkoff.tictactoe.integration.test;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.tictactoe.integration.IntegrationSettings;
import ru.tinkoff.tictactoe.session.GameService;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.SessionService;
import ru.tinkoff.tictactoe.session.model.Session;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static org.assertj.core.api.Assertions.assertThat;

class GameServiceTest extends IntegrationSettings {
    @Autowired
    private GameService gameService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionService sessionService;

    @Test
    void givenSessionWithTwoBots_whenStartGame_thenNewSessionHas10Turns() throws ExecutionException, InterruptedException, UnknownHostException {
        String firstBotIp = "1.1.1.1";
        int firstBotPort = 1111;
        String secondBotIp = "2.2.2.2";
        int secondBotPort = 2222;

//        String firstBotUri = URI.create(String.format("http:/%s:%d", firstBotIp, firstBotPort)).toString();
//        String secondBotUri = URI.create(String.format("http:/%s:%d", secondBotIp, secondBotPort)).toString();

        wireMockServer.stubFor(WireMock.post("/bot/turn")
                .withHost(equalTo(firstBotIp))
                .withPort(firstBotPort)
                .withRequestBody(equalToJson("{\"game_field\": \"" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"game_field\": \"" +
                                "_x_________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "\"}")));

        wireMockServer.stubFor(WireMock.post("/bot/turn")
                .withHost(equalTo(secondBotIp))
                .withPort(secondBotPort)
                .withRequestBody(equalToJson("{\"game_field\": \"" +
                        "_x_________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"game_field\": \"" +
                                "_xo________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "\"}")));
        wireMockServer.stubFor(WireMock.post("/bot/turn")
                .withHost(equalTo(firstBotIp))
                .withPort(firstBotPort)
                .withRequestBody(equalToJson("{\"game_field\": \"" +
                        "_xo________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"game_field\": \"" +
                                "_xo________________" +
                                "_x_________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "\"}")));
        wireMockServer.stubFor(WireMock.post("/bot/turn")
                .withHost(equalTo(secondBotIp))
                .withPort(secondBotPort)
                .withRequestBody(equalToJson("{\"game_field\": \"" +
                        "_xo________________" +
                        "_x_________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"game_field\": \"" +
                                "_xo________________" +
                                "_xo________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "\"}")));
        wireMockServer.stubFor(WireMock.post("/bot/turn")
                .withHost(equalTo(firstBotIp))
                .withPort(firstBotPort)
                .withRequestBody(equalToJson("{\"game_field\": \"" +
                        "_xo________________" +
                        "_xo________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"game_field\": \"" +
                                "_xo________________" +
                                "_xo________________" +
                                "_x_________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "\"}")));
        wireMockServer.stubFor(WireMock.post("/bot/turn")
                .withHost(equalTo(secondBotIp))
                .withPort(secondBotPort)
                .withRequestBody(equalToJson("{\"game_field\": \"" +
                        "_xo________________" +
                        "_xo________________" +
                        "_x_________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"game_field\": \"" +
                                "_xo________________" +
                                "_xo________________" +
                                "_xo________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "\"}")));
        wireMockServer.stubFor(WireMock.post("/bot/turn")
                .withHost(equalTo(firstBotIp))
                .withPort(firstBotPort)
                .withRequestBody(equalToJson("{\"game_field\": \"" +
                        "_xo________________" +
                        "_xo________________" +
                        "_xo________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"game_field\": \"" +
                                "_xo________________" +
                                "_xo________________" +
                                "_xo________________" +
                                "_x_________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "\"}")));
        wireMockServer.stubFor(WireMock.post("/bot/turn")
                .withHost(equalTo(secondBotIp))
                .withPort(secondBotPort)
                .withRequestBody(equalToJson("{\"game_field\": \"" +
                        "_xo________________" +
                        "_xo________________" +
                        "_xo________________" +
                        "_x_________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"game_field\": \"" +
                                "_xo________________" +
                                "_xo________________" +
                                "_xo________________" +
                                "_xo________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "\"}")));
        wireMockServer.stubFor(WireMock.post("/bot/turn")
                .withHost(equalTo(firstBotIp))
                .withPort(firstBotPort)
                .withRequestBody(equalToJson("{\"game_field\": \"" +
                        "_xo________________" +
                        "_xo________________" +
                        "_xo________________" +
                        "_xo________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "\"}"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"game_field\": \"" +
                                "_xo________________" +
                                "_xo________________" +
                                "_xo________________" +
                                "_xo________________" +
                                "_x_________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "___________________" +
                                "\"}")));
//         TODO: вопросик тут есть
        Session session = sessionService.createSession();
        sessionRepository.setFirstBotIpAndFirstBotPort(session.getId(), InetAddress.getByName(firstBotIp), firstBotPort);
        sessionRepository.setSecondBotIpAndSecondBotPort(session.getId(), InetAddress.getByName(secondBotIp), secondBotPort);
        CompletableFuture<Session> result = gameService.startGame(session.getId());
        Session newSession = result.get();
        assertThat(newSession.getTurns()).hasSize(10);
    }
}
