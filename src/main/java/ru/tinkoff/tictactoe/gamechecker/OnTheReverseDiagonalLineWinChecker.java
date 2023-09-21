package ru.tinkoff.tictactoe.gamechecker;

import static ru.tinkoff.tictactoe.session.Figure.getCrossOutFigure;

import org.springframework.stereotype.Component;
import ru.tinkoff.tictactoe.session.Figure;

@Component
public class OnTheReverseDiagonalLineWinChecker implements WinChecker {

    /**
     * Searches for 5 consecutive reverse diagonal figures
     * Like<br>
     * ____x<br>
     * ___x_<br>
     * __x__<br>
     * _x___<br>
     * x____<br>
     *
     * @param gameField the playing field on which we want to check the victory
     * @param figure the figure whose occurrences we are looking for
     * @return WinCheckResults
     */
    @Override
    public WinCheckerResults check(String gameField, Figure figure) {
        int count;
        for (int i = -14; i < 15; i++) {
            int col = Math.max(i, 0);
            int row = Math.min(18 + i, 18);
            count = 0;
            while (col < 19 && row > -1) {
                int indexInGameField = row * 19 + col;
                Figure curFigure = Figure.fromString(String.valueOf(gameField.charAt(indexInGameField)));
                if (curFigure.equals(figure)) {
                    ++count;
                    if (count == 5) {
                        char[] newGameField = gameField.toCharArray();
                        for (int j = 0; j < 5; j++) {
                            newGameField[indexInGameField + 18 * j] = getCrossOutFigure(figure).getName().charAt(0);
                        }
                        return new GameWinResult(new String(newGameField));
                    }
                } else {
                    count = 0;
                }
                ++col;
                --row;
            }
        }
        return GameContinuesResult.INSTANCE;
    }
}
