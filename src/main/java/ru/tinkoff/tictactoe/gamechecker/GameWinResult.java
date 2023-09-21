package ru.tinkoff.tictactoe.gamechecker;

public record GameWinResult(
    String newGameField
) implements WinCheckerResults {

    @Override
    public boolean win() {
        return true;
    }
}
