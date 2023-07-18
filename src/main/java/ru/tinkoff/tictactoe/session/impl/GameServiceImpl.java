package ru.tinkoff.tictactoe.session.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.tictactoe.session.GameService;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.client.BotClient;
import ru.tinkoff.tictactoe.client.BotRequest;
import ru.tinkoff.tictactoe.client.BotResponse;
import ru.tinkoff.tictactoe.session.model.Session;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService {
    private final SessionRepository sessionRepository;
    private final BotClient botClient;

    @Async
    @Transactional
    public CompletableFuture<String> startGame(UUID sessionId) {
        // TODO: Реализовать игру между двумя ботами
        // **Имитация игры**
        log.info("In session {} the game has started", sessionId);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("In session {} the delay has worked out", sessionId);

//        Session session = sessionRepository.findBySessionId(sessionId);
//        UUID firstBotId = session.getFirstBotId();
//        UUID secondBotId = session.getSecondBotId();
//        session.se

        BotResponse botResponse = null;
        try {
            botResponse = botClient.makeTurn(UUID.randomUUID(), BotRequest.builder().gameField("_oo___oo_xoxo__xoo_o_oxx_oo_x______o_______x___oo_x_x_o_x_______oxo___o_x__o_o______x__o_____x__o_____o______x_____xoxoo___xo_____o__x_x__________x__x____o_xo__x__o___x_______o_x______xo______oxo_x_xx__xox___ox____x_oo__ox_x_x___o__________x______________o_____x____o___x___xo___x__x_xo__x_x___ox___x_______x____x_o_x__x_o__ox__o__x__ox_x_____x_oo_____x____o_ox").build());
        } catch (Exception e) {
            log.error("In session {} the request fell with an error", sessionId);
            throw new RuntimeException(e);
        }
        log.info("In session {} the request worked with the result {}", sessionId, botResponse.getGameField());
        return CompletableFuture.completedFuture("Done");
    }
}
