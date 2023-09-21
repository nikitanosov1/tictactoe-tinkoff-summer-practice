package ru.tinkoff.tictactoe.session.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static ru.tinkoff.tictactoe.session.GameService.ZERO_TURN_GAME_FIELD;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import ru.tinkoff.tictactoe.client.BotClient;
import ru.tinkoff.tictactoe.client.BotRequest;
import ru.tinkoff.tictactoe.client.BotResponse;
import ru.tinkoff.tictactoe.gamechecker.GameChecker;
import ru.tinkoff.tictactoe.gamechecker.GameContinuesResult;
import ru.tinkoff.tictactoe.gamechecker.GameWinResult;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.model.SessionWithAllTurns;
import ru.tinkoff.tictactoe.session.model.SessionWithLastTurn;
import ru.tinkoff.tictactoe.turn.model.Turn;

class GameServiceImplTest {

    private static final Turn ZERO_TURN = Turn.builder()
        .gameField(ZERO_TURN_GAME_FIELD)
        .turn(0)
        .build();

    static UUID sessionId = UUID.randomUUID();
    static String firstBotIp = "1.1.1.1";
    static int firstBotPort = 1111;
    static String secondBotIp = "2.2.2.2";
    static int secondBotPort = 2222;
    static URI firstBotUri = URI.create(String.format("http://%s:%d", firstBotIp, firstBotPort));
    static URI secondBotUri = URI.create(String.format("http://%s:%d", secondBotIp, secondBotPort));

    private static final String ATTACKING_BOT_ID = "attacking";
    private static final String DEFENDING_BOT_ID = "defending";
    static SessionWithAllTurns.SessionWithAllTurnsBuilder sessionBuilder = SessionWithAllTurns.builder()
        .id(sessionId)
        .isActive(false)
        .attackingBotUrl("http://1.1.1.1:1111")
        .attackingBotId(ATTACKING_BOT_ID)
        .defendingBotUrl("http://2.2.2.2:2222")
        .defendingBotId(DEFENDING_BOT_ID)
        .createdAt(new Date())
        .updatedAt(new Date());

    private final SessionRepositoryMock sessionRepositoryMock = new SessionRepositoryMock();

    private final BotClient botClient = Mockito.mock(BotClient.class);

    private final GameChecker gameChecker = Mockito.mock(GameChecker.class);
    private final GameConfigs gameConfigs = new GameConfigs(
        Duration.ofNanos(1),
        19
    );

    private final GameServiceImpl gameService = new GameServiceImpl(
        sessionRepositoryMock,
        botClient,
        gameChecker,
        gameConfigs
    );

    {
        ReflectionTestUtils.setField(gameService, "gameService", gameService);
    }

    @BeforeEach
    void setUp() {
        sessionRepositoryMock.turnsHistory.clear();
        sessionRepositoryMock.addTurnToSession(sessionId, ZERO_TURN);
    }

    @Test
    void given_BothBotsPlayCorrectAndFirstWins_when_StartGame_then_GameFinishedCorrectly()
        throws InterruptedException {
        String turnGameField1 =
            "x________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField2 =
            "x____o___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField3 =
            "xx___o___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField4 =
            "xx___oo__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField5 =
            "xxx__oo__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField6 =
            "xxx__ooo_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField7 =
            "xxxx_ooo_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField8 =
            "xxxx_oooo________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField9 =
            "xxxxxoooo________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        final var lastWinField =
            "XXXXXoooo________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";

        when(botClient.makeTurn(firstBotUri, new BotRequest(ZERO_TURN_GAME_FIELD))).thenReturn(
            new BotResponse(turnGameField1));
        when(botClient.makeTurn(secondBotUri, new BotRequest(turnGameField1))).thenReturn(
            new BotResponse(turnGameField2));
        when(botClient.makeTurn(firstBotUri, new BotRequest(turnGameField2))).thenReturn(
            new BotResponse(turnGameField3));
        when(botClient.makeTurn(secondBotUri, new BotRequest(turnGameField3))).thenReturn(
            new BotResponse(turnGameField4));
        when(botClient.makeTurn(firstBotUri, new BotRequest(turnGameField4))).thenReturn(
            new BotResponse(turnGameField5));
        when(botClient.makeTurn(secondBotUri, new BotRequest(turnGameField5))).thenReturn(
            new BotResponse(turnGameField6));
        when(botClient.makeTurn(firstBotUri, new BotRequest(turnGameField6))).thenReturn(
            new BotResponse(turnGameField7));
        when(botClient.makeTurn(secondBotUri, new BotRequest(turnGameField7))).thenReturn(
            new BotResponse(turnGameField8));
        when(botClient.makeTurn(firstBotUri, new BotRequest(turnGameField8))).thenReturn(
            new BotResponse(turnGameField9));

        when(gameChecker.isWin(turnGameField1, Figure.CROSS)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField2, Figure.ZERO)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField3, Figure.CROSS)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField4, Figure.ZERO)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField5, Figure.CROSS)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField6, Figure.ZERO)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField7, Figure.CROSS)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField8, Figure.ZERO)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField9, Figure.CROSS)).thenReturn(new GameWinResult(lastWinField));
        gameService.startGame(sessionId);

        assertThat(sessionRepositoryMock.turnsHistory)
            .hasSize(10)
            .usingRecursiveFieldByFieldElementComparator()
            .containsExactly(
                ZERO_TURN,
                Turn.builder().gameField(turnGameField1).turn(1).build(),
                Turn.builder().gameField(turnGameField2).turn(2).build(),
                Turn.builder().gameField(turnGameField3).turn(3).build(),
                Turn.builder().gameField(turnGameField4).turn(4).build(),
                Turn.builder().gameField(turnGameField5).turn(5).build(),
                Turn.builder().gameField(turnGameField6).turn(6).build(),
                Turn.builder().gameField(turnGameField7).turn(7).build(),
                Turn.builder().gameField(turnGameField8).turn(8).build(),
                Turn.builder().gameField(lastWinField).turn(9).build()
            );
    }

    @Test
    void given_SecondBotResponsesWithError_when_StartGame_then_FirstBotWins()
        throws InterruptedException {
        String turnGameField1 =
            "x________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField2 =
            "x____o___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField3 =
            "xx___o___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField4 =
            "xx___oo__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField5 =
            "xxx__oo__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField6 =
            "xxx__ooo_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField7 =
            "xxxx_ooo_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField8 =
            "xxxx_oooo________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField9 =
            "xxxxxoooo________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        final var lastWinField =
            "XXXXXoooo________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";

        when(botClient.makeTurn(firstBotUri, new BotRequest(ZERO_TURN_GAME_FIELD))).thenReturn(
            new BotResponse(turnGameField1));
        when(botClient.makeTurn(secondBotUri, new BotRequest(turnGameField1))).thenReturn(
            new BotResponse(turnGameField2));
        when(botClient.makeTurn(firstBotUri, new BotRequest(turnGameField2))).thenReturn(
            new BotResponse(turnGameField3));
        when(botClient.makeTurn(secondBotUri, new BotRequest(turnGameField3))).thenReturn(
            new BotResponse(turnGameField4));
        when(botClient.makeTurn(firstBotUri, new BotRequest(turnGameField4))).thenReturn(
            new BotResponse(turnGameField5));
        when(botClient.makeTurn(secondBotUri, new BotRequest(turnGameField5))).thenReturn(
            new BotResponse(turnGameField6));
        when(botClient.makeTurn(firstBotUri, new BotRequest(turnGameField6))).thenReturn(
            new BotResponse(turnGameField7));
        when(botClient.makeTurn(secondBotUri, new BotRequest(turnGameField7))).thenReturn(
            new BotResponse(turnGameField8));
        when(botClient.makeTurn(firstBotUri, new BotRequest(turnGameField8))).thenReturn(
            new BotResponse(turnGameField9));

        when(gameChecker.isWin(turnGameField1, Figure.CROSS)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField2, Figure.ZERO)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField3, Figure.CROSS)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField4, Figure.ZERO)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField5, Figure.CROSS)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField6, Figure.ZERO)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField7, Figure.CROSS)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField8, Figure.ZERO)).thenReturn(GameContinuesResult.INSTANCE);
        when(gameChecker.isWin(turnGameField9, Figure.CROSS)).thenReturn(new GameWinResult(lastWinField));
        gameService.startGame(sessionId);

        assertThat(sessionRepositoryMock.turnsHistory)
            .hasSize(10)
            .usingRecursiveFieldByFieldElementComparator()
            .containsExactly(
                ZERO_TURN,
                Turn.builder().gameField(turnGameField1).turn(1).build(),
                Turn.builder().gameField(turnGameField2).turn(2).build(),
                Turn.builder().gameField(turnGameField3).turn(3).build(),
                Turn.builder().gameField(turnGameField4).turn(4).build(),
                Turn.builder().gameField(turnGameField5).turn(5).build(),
                Turn.builder().gameField(turnGameField6).turn(6).build(),
                Turn.builder().gameField(turnGameField7).turn(7).build(),
                Turn.builder().gameField(turnGameField8).turn(8).build(),
                Turn.builder().gameField(lastWinField).turn(9).build()
            );
        assertThat(sessionRepositoryMock.winBot).isEqualTo(ATTACKING_BOT_ID);
    }

    private static class SessionRepositoryMock implements SessionRepository {

        private final List<Turn> turnsHistory = new ArrayList<>();
        private String winBot = null;

        @Override
        public SessionWithAllTurns findFetchTurnsBySessionId(UUID sessionId) {
            return sessionBuilder.turns(turnsHistory).build();
        }

        @Override
        public void finishSession(UUID sessionId, String winBot) {
            this.winBot = winBot;
        }

        @Override
        public Session findBySessionId(UUID sessionId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public SessionWithLastTurn findFetchLastTurnBySessionId(UUID sessionId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addTurnToSession(UUID sessionId, Turn turn) {
            turnsHistory.add(turn);
        }

        @Override
        public Session createSession() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAttackingBot(UUID sessionId, String url, String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setDefendingBot(UUID sessionId, String url, String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Session> findAllSessions() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Session> findSessionsByIsActive(Boolean isActive) {
            throw new UnsupportedOperationException();
        }
    }
}