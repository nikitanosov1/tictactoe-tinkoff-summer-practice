package ru.tinkoff.tictactoe.gamechecker;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.tinkoff.tictactoe.session.Figure;

@Component
@Order(1)
public class CorrectSizeValidChecker implements ValidChecker {
    @Override
    public ValidCheckerResults check(String currGameField, String newGameField, Figure figure) {
        ValidCheckerResults results = new ValidCheckerResults();
        if (newGameField == null || newGameField.length() != 19 * 19) {
            results.setIsValid(false);
            return results;
        }
        results.setIsValid(true);
        return results;
    }
}
