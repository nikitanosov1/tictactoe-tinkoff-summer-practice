package ru.tinkoff.tictactoe.session.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.tictactoe.client.BotClient;
import ru.tinkoff.tictactoe.client.BotRequest;
import ru.tinkoff.tictactoe.client.BotResponse;
import ru.tinkoff.tictactoe.gamechecker.GameChecker;
import ru.tinkoff.tictactoe.gamechecker.ValidCheckerResults;
import ru.tinkoff.tictactoe.gamechecker.WinCheckerResults;
import ru.tinkoff.tictactoe.session.*;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.turn.model.Turn;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService {
    private final SessionRepository sessionRepository;
    private final BotClient botClient;
    private final GameChecker gameChecker;
    
    @Async
    public CompletableFuture<Session> startGame(UUID sessionId) {
        log.info("In session {} the game has started", sessionId);
        Session session = sessionRepository.findBySessionId(sessionId);
        UUID attackingBotId = session.getFirstBotId();
        UUID defendingBotId = session.getSecondBotId();

        while (isBothBotsAlive(session, 2)) {
            int currTurn = session.getTurns().size();
            log.info("In session {} the turn {} has begun with attackingBotId {} and defendingBotId {}", sessionId, currTurn, attackingBotId, defendingBotId);
            Figure attackingBotFigure = (currTurn % 2 == 0) ? Figure.ZERO : Figure.CROSS;
            String currGameField = session.getTurns().get(currTurn - 1).getGameField();

            Turn newTurn = Turn.builder()
                    .gameField(currGameField)
                    .turn(currTurn)
                    .build();

            // Стучим attackingBot, чтобы он сделал ход
            BotResponse attackingBotResponse = botClient.makeTurn(attackingBotId, new BotRequest(currGameField));
            // TODO: Добавить логику на случай, если makeTurn кидает exception
            String newGameField = attackingBotResponse.getGameField();
            log.info("In session {} newGameField = {}", sessionId, newGameField);

            // Проверяем, что в поле изменилось именно одно поле с символа _ на символ attackingBotFigure
            ValidCheckerResults validCheckerResults = gameChecker.isValidTurn(currGameField, newGameField, attackingBotFigure);
            if (Boolean.TRUE.equals(validCheckerResults.getIsValid())) {
                newTurn.setGameField(newGameField);
            } else {
                // Если бот ответил невалидным полем, то не меняем поле и передаем ход другому боту
                session.getTurns().add(newTurn);
                sessionRepository.addTurnToSession(session.getId(), newTurn);

                UUID temp = attackingBotId;
                attackingBotId = defendingBotId;
                defendingBotId = temp;
                continue;
            }

            // Проверяем, не победил ли attackingBot своим ходом
            WinCheckerResults winCheckerResults = gameChecker.isWin(newGameField, attackingBotFigure);
            if (winCheckerResults.getIsWin().equals(Boolean.TRUE)) {
                log.info("In session {} bot {} win!", sessionId, attackingBotId);
                newTurn.setGameField(winCheckerResults.getNewGameField());
                session.getTurns().add(newTurn);
                sessionRepository.addTurnToSession(session.getId(), newTurn);
                break;
            }
            session.getTurns().add(newTurn);
            sessionRepository.addTurnToSession(session.getId(), newTurn);

            // Меняем attackingBotId с defendingBotId
            UUID temp = attackingBotId;
            attackingBotId = defendingBotId;
            defendingBotId = temp;
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
