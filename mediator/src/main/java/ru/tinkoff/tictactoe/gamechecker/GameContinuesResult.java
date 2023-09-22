package ru.tinkoff.tictactoe.gamechecker;

public final class GameContinuesResult implements WinCheckerResults {

    public static final GameContinuesResult INSTANCE = new GameContinuesResult();

    private GameContinuesResult() {
    }

    @Override
    public boolean win() {
        return false;
    }
}
