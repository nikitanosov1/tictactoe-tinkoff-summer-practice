package ru.tinkoff.tictactoe.gamechecker.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.tictactoe.gamechecker.*;
import ru.tinkoff.tictactoe.gamechecker.exception.UnsupportedFigureException;
import ru.tinkoff.tictactoe.session.Figure;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameCheckerImpl implements GameChecker {
    private final List<? extends WinChecker> winCheckers;
    private final List<? extends ValidChecker> validCheckers;

    /**
     * Searches for 5 consecutive figures
     *
     * @param gameField the playing field on which we want to check the victory
     * @param figure    the figure whose occurrences we are looking for
     * @return WinCheckResults
     */
    @Override
    public WinCheckerResults isWin(String gameField, Figure figure) {
        if (!figure.equals(Figure.CROSS) && !figure.equals(Figure.ZERO)) {
            throw new UnsupportedFigureException();
        }
        for (WinChecker winChecker : winCheckers) {
            WinCheckerResults winCheckerResults = winChecker.check(gameField, figure);
            if (Boolean.TRUE.equals(winCheckerResults.getIsWin())) {
                return winCheckerResults;
            }
        }
        return WinCheckerResults.builder()
                .isWin(false)
                .build();
    }

    @Override
    public ValidCheckerResults isValidTurn(String currGameField, String newGameField, Figure figure) {
        if (!figure.equals(Figure.CROSS) && !figure.equals(Figure.ZERO)) {
            throw new UnsupportedFigureException();
        }
        for (ValidChecker validChecker : validCheckers) {
            ValidCheckerResults validCheckerResults = validChecker.check(currGameField, newGameField, figure);
            if (Boolean.FALSE.equals(validCheckerResults.getIsValid())) {
                return validCheckerResults;
            }
        }
        return ValidCheckerResults.builder()
                .isValid(true)
                .build();
    }
}
