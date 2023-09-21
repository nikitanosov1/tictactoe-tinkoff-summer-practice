package ru.tinkoff.tictactoe.gamechecker;

public sealed interface WinCheckerResults permits GameContinuesResult, GameWinResult {
    boolean win();
}
