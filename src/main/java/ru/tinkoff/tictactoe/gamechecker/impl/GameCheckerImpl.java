package ru.tinkoff.tictactoe.gamechecker.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.tictactoe.gamechecker.GameChecker;
import ru.tinkoff.tictactoe.gamechecker.GameContinuesResult;
import ru.tinkoff.tictactoe.gamechecker.ValidChecker;
import ru.tinkoff.tictactoe.gamechecker.WinChecker;
import ru.tinkoff.tictactoe.gamechecker.WinCheckerResults;
import ru.tinkoff.tictactoe.session.Figure;

@RequiredArgsConstructor
@Service
public class GameCheckerImpl implements GameChecker {

    private final List<? extends WinChecker> winCheckers;
    private final List<? extends ValidChecker> validCheckers;

    /**
     * Searches for 5 consecutive figures
     *
     * @param gameField the playing field on which we want to check the victory
     * @param figure the figure whose occurrences we are looking for
     * @return WinCheckResults
     */
    @Override
    public WinCheckerResults isWin(String gameField, Figure figure) {
        for (WinChecker winChecker : winCheckers) {
            WinCheckerResults winCheckerResults = winChecker.check(gameField, figure);
            if (Boolean.TRUE.equals(winCheckerResults.win())) {
                return winCheckerResults;
            }
        }
        return GameContinuesResult.INSTANCE;
    }

    @Override
    public void validate(String currGameField, String newGameField, Figure figure) {
        for (ValidChecker validChecker : validCheckers) {
            validChecker.validate(currGameField, newGameField, figure);
        }
    }
}
