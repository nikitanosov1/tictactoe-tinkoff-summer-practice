package ru.tinkoff.tictactoe.gamechecker;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.tinkoff.tictactoe.gamechecker.exception.CellChangedIncorrectlyInTurnException;
import ru.tinkoff.tictactoe.gamechecker.exception.NotOneFigureChangedException;
import ru.tinkoff.tictactoe.session.Figure;

@Component
@Order(10)
public class OneFigureChangedValidChecker implements ValidChecker {
    @Override
    public void validate(String currGameField, String newGameField, Figure figure) {
        int countDiffFigures = 0;
        for (int i = 0; i < newGameField.length(); i++) {
            Figure currFigure = Figure.fromString(String.valueOf(currGameField.charAt(i)));
            Figure newFigure = Figure.fromString(String.valueOf(newGameField.charAt(i)));
            if (currFigure.equals(Figure.EMPTY) && newFigure.equals(figure)) {
                ++countDiffFigures;
                continue;
            }
            if (!currFigure.equals(newFigure)) {
                throw new CellChangedIncorrectlyInTurnException();
            }
        }
        if (countDiffFigures != 1) {
            throw new NotOneFigureChangedException();
        }
    }
}
