package ru.tinkoff.tictactoe.gamechecker;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.tinkoff.tictactoe.gamechecker.exception.CellChangedIncorrectlyInTurnException;
import ru.tinkoff.tictactoe.gamechecker.exception.NotOneFigureChangedException;
import ru.tinkoff.tictactoe.gamechecker.exception.IncorrectFigureException;
import ru.tinkoff.tictactoe.session.Figure;

@Component
@Order(10)
public class OneFigureChangedValidChecker implements ValidChecker {

    @Override
    public void validate(String currGameField, String newGameField, Figure figure) {
        int countDiffFigures = 0;
        for (int i = 0; i < newGameField.length(); i++) {
            String currFigure = String.valueOf(currGameField.charAt(i));
            String newFigure = String.valueOf(newGameField.charAt(i));
            if (currFigure.equals(newFigure)) {
                continue;
            }
            if (!Figure.EMPTY.getName().equals(currFigure)){
                throw new CellChangedIncorrectlyInTurnException();
            }
            if (!figure.getName().equals(newFigure)) {
                throw new IncorrectFigureException();
            }
            ++countDiffFigures;
        }
        if (countDiffFigures != 1) {
            throw new NotOneFigureChangedException();
        }
    }
}
