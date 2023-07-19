package ru.tinkoff.tictactoe.session;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntityRepository;
import ru.tinkoff.tictactoe.turn.persistance.postgres.TurnEntityRepository;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest(httpPort = 8081)
class GameServiceTest {
    //    @Value("${bots.host}")
//    String BOTS_HOST;
//
//    @Value("${bots.port}")
//    Integer BOTS_PORT;
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private GameService gameService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    void tempName() throws ExecutionException, InterruptedException {
        UUID firstBotId = UUID.randomUUID();
        UUID secondBotId = UUID.randomUUID();
        WireMock.stubFor(WireMock.post(WireMock.urlMatching("/bot/turn/(.*)"))
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

        WireMock.stubFor(WireMock.post(WireMock.urlMatching("/bot/turn/(.*)"))
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
        WireMock.stubFor(WireMock.post(WireMock.urlMatching("/bot/turn/(.*)"))
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
        WireMock.stubFor(WireMock.post(WireMock.urlMatching("/bot/turn/(.*)"))
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
        WireMock.stubFor(WireMock.post(WireMock.urlMatching("/bot/turn/(.*)"))
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
        WireMock.stubFor(WireMock.post(WireMock.urlMatching("/bot/turn/(.*)"))
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
        WireMock.stubFor(WireMock.post(WireMock.urlMatching("/bot/turn/(.*)"))
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
        WireMock.stubFor(WireMock.post(WireMock.urlMatching("/bot/turn/(.*)"))
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
        WireMock.stubFor(WireMock.post(WireMock.urlMatching("/bot/turn/(.*)"))
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
        // TODO: вопросик тут есть
        Session session = sessionRepository.createSession();
        sessionRepository.setFirstBotId(session.getId(), firstBotId);
        sessionRepository.setSecondBotId(session.getId(), secondBotId);
        CompletableFuture<Session> result = gameService.startGame(session.getId());
        Session newSession = result.get();
        assertThat(newSession.getTurns()).hasSize(10);
    }
}
