package ru.tinkoff.tictactoe.session.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.tictactoe.client.BotClient;
import ru.tinkoff.tictactoe.client.BotRequest;
import ru.tinkoff.tictactoe.client.BotResponse;
import ru.tinkoff.tictactoe.gamechecker.GameChecker;
import ru.tinkoff.tictactoe.gamechecker.WinCheckerResults;
import ru.tinkoff.tictactoe.gamechecker.exception.ValidCheckerException;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.GameService;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.turn.model.Turn;

import java.net.InetAddress;
import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService {
    private final SessionRepository sessionRepository;
    private final BotClient botClient;
    private final GameChecker gameChecker;

    @Value("${bots.step-for-bots-alive}")
    private int steps;

    @Async
    public CompletableFuture<Session> startGame(UUID sessionId) {
        log.info("In session {} the game has started", sessionId);
        Session session = sessionRepository.findBySessionId(sessionId);

        URI firstBotUri = URI.create(String.format("http:/%s:%d", session.getFirstBotIp(), session.getFirstBotPort()));
        URI secondBotUri = URI.create(String.format("http:/%s:%d", session.getSecondBotIp(), session.getSecondBotPort()));
        log.info("In session {} firstBotUri = {} and secondBotUri = {}", sessionId, firstBotUri, secondBotUri);

        URI attackingBotURI = firstBotUri;
        URI defendingBotURI = secondBotUri;

        while (isBothBotsAlive(session, steps)) {
            int currTurn = session.getTurns().size();
            log.info("In session {} the turn {} has begun with attackingBotURI {} and defendingBotURI {}", sessionId, currTurn, attackingBotURI, defendingBotURI);
            Figure attackingBotFigure = (currTurn % 2 == 0) ? Figure.ZERO : Figure.CROSS;
            String currGameField = session.getTurns().get(currTurn - 1).getGameField();

            Turn newTurn = Turn.builder()
                    .gameField(currGameField)
                    .turn(currTurn)
                    .build();

            // Стучим attackingBot, чтобы он сделал ход
            BotResponse attackingBotResponse = botClient.makeTurn(attackingBotURI, new BotRequest(currGameField));
            // TODO: Добавить логику на случай, если makeTurn кидает exception
            String newGameField = attackingBotResponse.getGameField();
            log.info("In session {} newGameField = {}", sessionId, newGameField);

            // Проверяем, что в поле изменилось именно одно поле с символа _ на символ attackingBotFigure
            try {
                gameChecker.validate(currGameField, newGameField, attackingBotFigure);
                newTurn.setGameField(newGameField);
            } catch (ValidCheckerException e) {
                // Если бот ответил невалидным полем, то не меняем поле и передаем ход другому боту
                session.getTurns().add(newTurn);
                sessionRepository.addTurnToSession(session.getId(), newTurn);

                URI temp = attackingBotURI;
                attackingBotURI = defendingBotURI;
                defendingBotURI = temp;
                continue;
            }

            // Проверяем, не победил ли attackingBot своим ходом
            WinCheckerResults winCheckerResults = gameChecker.isWin(newGameField, attackingBotFigure);
            if (winCheckerResults.getIsWin().equals(Boolean.TRUE)) {
                log.info("In session {} bot {} win!", sessionId, attackingBotURI);
                newTurn.setGameField(winCheckerResults.getNewGameField());
                session.getTurns().add(newTurn);
                sessionRepository.addTurnToSession(session.getId(), newTurn);
                break;
            }
            session.getTurns().add(newTurn);
            sessionRepository.addTurnToSession(session.getId(), newTurn);

            // Меняем attackingBotId с defendingBotId
            URI temp = attackingBotURI;
            attackingBotURI = defendingBotURI;
            defendingBotURI = temp;
        }
        log.info("The game ended in session {} ", sessionId);
        return CompletableFuture.completedFuture(session);
    }

    public boolean isBothBotsAlive(Session session, int steps) {
        int turns = session.getTurns().size();
        if (turns < 3) {
            return true;
        }
        String prevGameField = session.getTurns().get(turns - 1 - steps).getGameField();
        String currGameField = session.getTurns().get(turns - 1).getGameField();
        // Если игровое поле не меняется steps ходов подряд, значит боты не живы
        return !prevGameField.equals(currGameField);
    }
}
