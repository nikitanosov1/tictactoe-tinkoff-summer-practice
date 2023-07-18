package ru.tinkoff.tictactoe;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.tinkoff.tictactoe.session.GameService;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@WireMockTest(httpPort = 8081)
class TictactoeApplicationTests {
//    @Value("${bots.host}")
//    String BOTS_HOST;
//
//    @Value("${bots.port}")
//    Integer BOTS_PORT;

    @Autowired
    private GameService gameService;

    @Test
    void tempName() throws ExecutionException, InterruptedException {
        WireMock.stubFor(WireMock.post(WireMock.urlMatching("/bot/turn/(.*)"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"game_field": "xoo___oo_xoxo__xoo_o_oxx_oo_x______o_______x___oo_x_x_o_x_______oxo___o_x__o_o______x__o_____x__o_____o______x_____xoxoo___xo_____o__x_x__________x__x____o_xo__x__o___x_______o_x______xo______oxo_x_xx__xox___ox____x_oo__ox_x_x___o__________x______________o_____x____o___x___xo___x__x_xo__x_x___ox___x_______x____x_o_x__x_o__ox__o__x__ox_x_____x_oo_____x____o_ox"}
                                """)));

        CompletableFuture<String> result = gameService.startGame(UUID.randomUUID());
        assertThat(result.get()).isEqualTo("Done");
    }
}
