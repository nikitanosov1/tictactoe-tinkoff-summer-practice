package ru.tinkoff.tictactoe.gamechecker;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.tinkoff.tictactoe.session.Figure;

@Component
@Order(2)
public class OneFigureChangedValidChecker implements ValidChecker {
    @Override
    public ValidCheckerResults check(String currGameField, String newGameField, Figure figure) {
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
