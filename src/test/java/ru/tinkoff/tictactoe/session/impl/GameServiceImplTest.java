package ru.tinkoff.tictactoe.session.impl;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.tinkoff.tictactoe.client.BotClient;
import ru.tinkoff.tictactoe.client.BotRequest;
import ru.tinkoff.tictactoe.client.BotResponse;
import ru.tinkoff.tictactoe.gamechecker.GameChecker;
import ru.tinkoff.tictactoe.gamechecker.WinCheckerResults;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.turn.model.Turn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GameServiceImplTest {
    @Mock
    private SessionRepository sessionRepositoryMock;

    @Mock
    private BotClient botClient;

    @Mock
    private GameChecker gameChecker;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    public void before() throws Exception {
        FieldUtils.writeField(gameService, "steps", 2, true);
    }

    @Test
    void givenSessionId_whenStartGame_thenReturnSessionWithNewTurns() throws ExecutionException, InterruptedException {
        UUID sessionId = UUID.randomUUID();
        UUID firstBotId = UUID.randomUUID();
        UUID secondBotId = UUID.randomUUID();
        Session session = Session.builder()
                .id(sessionId)
                .isActive(false)
                .firstBotId(firstBotId)
                .secondBotId(secondBotId)
                .turns(new ArrayList<>(List.of(Turn.builder()
                        .gameField("_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________")
                        .turn(0)
                        .build())
                ))
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        String turnGameField0 = "_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField1 = "x________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField2 = "x____o___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField3 = "xx___o___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField4 = "xx___oo__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField5 = "xxx__oo__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField6 = "xxx__ooo_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField7 = "xxxx_ooo_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField8 = "xxxx_oooo________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String turnGameField9 = "xxxxxoooo________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";

        when(sessionRepositoryMock.findBySessionId(sessionId)).thenReturn(session);
        when(botClient.makeTurn(firstBotId, new BotRequest(turnGameField0))).thenReturn(new BotResponse(turnGameField1));
        when(botClient.makeTurn(secondBotId, new BotRequest(turnGameField1))).thenReturn(new BotResponse(turnGameField2));
        when(botClient.makeTurn(firstBotId, new BotRequest(turnGameField2))).thenReturn(new BotResponse(turnGameField3));
        when(botClient.makeTurn(secondBotId, new BotRequest(turnGameField3))).thenReturn(new BotResponse(turnGameField4));
        when(botClient.makeTurn(firstBotId, new BotRequest(turnGameField4))).thenReturn(new BotResponse(turnGameField5));
        when(botClient.makeTurn(secondBotId, new BotRequest(turnGameField5))).thenReturn(new BotResponse(turnGameField6));
        when(botClient.makeTurn(firstBotId, new BotRequest(turnGameField6))).thenReturn(new BotResponse(turnGameField7));
        when(botClient.makeTurn(secondBotId, new BotRequest(turnGameField7))).thenReturn(new BotResponse(turnGameField8));
        when(botClient.makeTurn(firstBotId, new BotRequest(turnGameField8))).thenReturn(new BotResponse(turnGameField9));

        when(gameChecker.isWin(turnGameField1, Figure.CROSS)).thenReturn(WinCheckerResults.builder().isWin(false).build());
        when(gameChecker.isWin(turnGameField2, Figure.ZERO)).thenReturn(WinCheckerResults.builder().isWin(false).build());
        when(gameChecker.isWin(turnGameField3, Figure.CROSS)).thenReturn(WinCheckerResults.builder().isWin(false).build());
        when(gameChecker.isWin(turnGameField4, Figure.ZERO)).thenReturn(WinCheckerResults.builder().isWin(false).build());
        when(gameChecker.isWin(turnGameField5, Figure.CROSS)).thenReturn(WinCheckerResults.builder().isWin(false).build());
        when(gameChecker.isWin(turnGameField6, Figure.ZERO)).thenReturn(WinCheckerResults.builder().isWin(false).build());
        when(gameChecker.isWin(turnGameField7, Figure.CROSS)).thenReturn(WinCheckerResults.builder().isWin(false).build());
        when(gameChecker.isWin(turnGameField8, Figure.ZERO)).thenReturn(WinCheckerResults.builder().isWin(false).build());
        when(gameChecker.isWin(turnGameField9, Figure.CROSS)).thenReturn(WinCheckerResults.builder()
                .isWin(true)
                .newGameField("XXXXXoooo________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________")
                .build());
        Session newSession = gameService.startGame(sessionId).get();

        assertThat(newSession.getTurns()).hasSize(10);
    }
}