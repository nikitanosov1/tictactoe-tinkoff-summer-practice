package ru.tinkoff.tictactoe.session;

import java.util.UUID;

public interface GameService {

    String ZERO_TURN_GAME_FIELD =
        "_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
    Figure ATTACKING_BOT_FIGURE = Figure.CROSS;
    Figure DEFENDING_BOT_FIGURE = Figure.ZERO;

    void startGame(UUID sessionId) throws InterruptedException;

    void makeNewTurn(UUID sessionId) throws InterruptedException;
}
