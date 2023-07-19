package ru.tinkoff.tictactoe.gamechecker;

import org.springframework.stereotype.Component;
import ru.tinkoff.tictactoe.session.Figure;

import static ru.tinkoff.tictactoe.session.Figure.getCrossOutFigure;

@Component
class OnTheHorizontalLineWinChecker implements WinChecker {
    /**
     * Searches for 5 consecutive horizontal figures
     * Like<br>
     * _____<br>
     * xxxxx<br>
     * _____<br>
     * _____<br>
     * _____<br>
     *
     * @param gameField the playing field on which we want to check the victory
     * @param figure    the figure whose occurrences we are looking for
     * @return WinCheckResults
     */
    @Override
    public WinCheckerResults check(String gameField, Figure figure) {
        WinCheckerResults results = WinCheckerResults.builder()
                .isWin(false)
                .build();
        int count = 0;
        for (int row = 0; row < 19; row++) {
            for (int col = 0; col < 19; col++) {
                int indexInGameField = row * 19 + col;
                Figure curFigure = Figure.fromString(String.valueOf(gameField.charAt(indexInGameField)));
                if (curFigure.equals(figure)) {
                    ++count;
                    if (count == 5) {
                        char[] newGameField = gameField.toCharArray();
                        for (int i = 0; i < 5; i++) {
                            newGameField[indexInGameField - i] = getCrossOutFigure(figure).getName().charAt(0);
                        }
                        results.setIsWin(true);
                        results.setNewGameField(new String(newGameField));
                        return results;
                    }
                } else {
                    count = 0;
                }
            }
            count = 0;
        }
        return results;
    }
}
