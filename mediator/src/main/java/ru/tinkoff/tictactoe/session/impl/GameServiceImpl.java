package ru.tinkoff.tictactoe.session.impl;

import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.tictactoe.client.BotClient;
import ru.tinkoff.tictactoe.client.BotRequest;
import ru.tinkoff.tictactoe.client.BotResponse;
import ru.tinkoff.tictactoe.client.exception.BotResponseStatusCodeNotOkException;
import ru.tinkoff.tictactoe.gamechecker.GameChecker;
import ru.tinkoff.tictactoe.gamechecker.GameWinResult;
import ru.tinkoff.tictactoe.gamechecker.WinCheckerResults;
import ru.tinkoff.tictactoe.gamechecker.exception.ValidCheckerException;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.GameService;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.model.SessionWithAllTurns;
import ru.tinkoff.tictactoe.turn.model.Turn;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService {

    private static final String ATTACKING_BOT_NAME = "attacking";
    private static final String DEFENDING_BOT_NAME = "defending";
    private final SessionRepository sessionRepository;
    private final BotClient botClient;
    private final GameChecker gameChecker;

    private final GameConfigs gameConfigs;

    @Autowired
    @Lazy
    private GameService gameService;

    @Transactional
    public void startGame(UUID sessionId) throws InterruptedException {
        sessionRepository.startSession(sessionId);
        gameService.makeNewTurn(sessionId);
    }

    @Async
    @Override
    public void makeNewTurn(UUID sessionId) throws InterruptedException {
        log.trace("Next turn in session {}", sessionId);
        SessionWithAllTurns session = sessionRepository.findFetchTurnsBySessionId(sessionId);
        final var turnsHistory = session.turns();
        int currTurn = turnsHistory.size();
        final URI currentBotUri;
        final String currentBotId;
        final String oppositeBotId;
        final Figure currentFigure;
        String currGameField = turnsHistory.get(currTurn - 1).getGameField();
        switch (currTurn % 2 != 0 ? ATTACKING_BOT_NAME : DEFENDING_BOT_NAME) {
            case ATTACKING_BOT_NAME -> {
                currentBotUri = URI.create(session.attackingBotUrl());
                currentBotId = session.attackingBotId();
                oppositeBotId = session.defendingBotId();
                currentFigure = ATTACKING_BOT_FIGURE;
            }
            case DEFENDING_BOT_NAME -> {
                currentBotUri = URI.create(session.defendingBotUrl());
                currentBotId = session.defendingBotId();
                oppositeBotId = session.attackingBotId();
                currentFigure = DEFENDING_BOT_FIGURE;
            }
            default -> throw new IllegalStateException();
        }
        log.trace("{} bot makes turn {}", currentBotId, currTurn);
        final var turnBuilder = Turn.builder()
            .turn(currTurn)
            .gameField(currGameField);

        // Делаем ход одним из ботов
        final BotResponse botResponse;
        try {
            botResponse = botClient.makeTurn(currentBotUri, new BotRequest(currGameField));
        } catch (BotResponseStatusCodeNotOkException e) {
            // Если бот ответил ошибкой, тогда заканчиваем игру
            log.warn("Bot {} responded not 200 http code", currentBotId);
            finishGame(sessionId, oppositeBotId);
            return;
        }
        String newGameField = botResponse.getGameField();

        // Проверяем, что в поле изменилось именно одно поле с символа _ на символ attackingBotFigure
        try {
            gameChecker.validate(currGameField, newGameField, currentFigure);
        } catch (ValidCheckerException e) {
            // Если бот ответил невалидным полем, то не меняем поле и передаем ход другому боту
            log.warn("Bot {} responded incorrect new field {}", currentBotId, newGameField);
            finishGame(sessionId, oppositeBotId);
            return;
        }

        WinCheckerResults winCheckerResults = gameChecker.isWin(newGameField, currentFigure);
        if (winCheckerResults.win()) {
            final var finishGameField = ((GameWinResult) winCheckerResults).newGameField();
            sessionRepository.addTurnToSession(session.id(), turnBuilder.gameField(finishGameField).build());
            finishGame(sessionId, currentBotId);
            return;
        }

        Turn newTurn = turnBuilder.gameField(newGameField).build();
        sessionRepository.addTurnToSession(session.id(), newTurn);
        log.debug("Bot {} finished his turn", currentBotId);
        Thread.sleep(gameConfigs.sleepDuration().toMillis());
        gameService.makeNewTurn(session.id());
    }

    private void finishGame(UUID sessionId, String botId) {
        log.info("GAME {} BOT {} WINS", sessionId, botId);
        sessionRepository.finishSession(sessionId, botId);
    }
}
