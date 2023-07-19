package ru.tinkoff.tictactoe.gamechecker.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.tictactoe.gamechecker.GameChecker;
import ru.tinkoff.tictactoe.gamechecker.ValidCheckerResults;
import ru.tinkoff.tictactoe.gamechecker.WinCheckerResults;
import ru.tinkoff.tictactoe.gamechecker.WinChecker;
import ru.tinkoff.tictactoe.session.Figure;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameCheckerImpl implements GameChecker {
    private final List<? extends WinChecker> winCheckers;

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
            throw new UnsupportedOperationException();
        }
        for (WinChecker winChecker : winCheckers) {
            WinCheckerResults winCheckerResults = winChecker.check(gameField, figure);
            if (winCheckerResults.getIsWin()) {
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
            throw new UnsupportedOperationException();
        }
        if (newGameField == null || newGameField.length() != 19 * 19) {
            return ValidCheckerResults.builder().isValid(false).build();
        }

        int countDiffFigures = 0;
        for (int i = 0; i < newGameField.length(); i++) {
            Figure currFigure = Figure.fromString(String.valueOf(currGameField.charAt(i)));
            Figure newFigure = Figure.fromString(String.valueOf(newGameField.charAt(i)));
            if (currFigure.equals(Figure.EMPTY) && newFigure.equals(figure)) {
                ++countDiffFigures;
                continue;
            }
            if (!currFigure.equals(newFigure)) {
                return ValidCheckerResults.builder().isValid(false).build();
            }
        }
        if (countDiffFigures != 1) {
            return ValidCheckerResults.builder().isValid(false).build();
        }
        return ValidCheckerResults.builder().isValid(true).build();
    }
}
