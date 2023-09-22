package ru.tinkoff.tictactoe.integration.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.tictactoe.gamechecker.exception.GameFieldIncorrectSizeException;
import ru.tinkoff.tictactoe.gamechecker.exception.IncorrectFigureException;
import ru.tinkoff.tictactoe.gamechecker.exception.NotOneFigureChangedException;
import ru.tinkoff.tictactoe.gamechecker.exception.ValidCheckerException;
import ru.tinkoff.tictactoe.gamechecker.impl.GameCheckerImpl;
import ru.tinkoff.tictactoe.integration.IntegrationSettings;
import ru.tinkoff.tictactoe.session.Figure;

class GameCheckerImplTest extends IntegrationSettings {

    @Autowired
    private GameCheckerImpl gameChecker;

    private static Stream<Arguments> argsForValidTurns() {
        return Stream.of(
            Arguments.of("" +
                             "___xx______________" +
                             "___________________" +
                             "______o____________" +
                             "_____o_____________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________"
                , "" +
                             "___xxx_____________" +
                             "___________________" +
                             "______o____________" +
                             "_____o_____________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________"
                ,
                         Figure.CROSS),
            Arguments.of("" +
                             "___xx______________" +
                             "___________________" +
                             "______o____________" +
                             "_____o_____________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________"
                , "" +
                             "___xxo_____________" +
                             "___________________" +
                             "______o____________" +
                             "_____o_____________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________"
                ,
                         Figure.ZERO)
        );
    }

    @ParameterizedTest
    @MethodSource("argsForValidTurns")
    void givenValidTurns_whenValidate_ThenDoesNotThrow(String currGameField, String newGameField, Figure figure) {
        assertDoesNotThrow(() ->
                               gameChecker.validate(currGameField, newGameField, figure)
        );
    }

    private static Stream<Arguments> argsForInvalidTurns() {
        return Stream.of(
            Arguments.of("" +
                             "___xx______________" +
                             "___________________" +
                             "______o____________" +
                             "_____o_____________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________"
                , "" +
                             "___xx______________" +
                             "_______o___________" +
                             "______o____________" +
                             "_____o_____________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________"
                ,
                         Figure.CROSS,
                         IncorrectFigureException.class
            ),
            Arguments.of("" +
                             "___xx______________" +
                             "___________________" +
                             "______o____________" +
                             "_____o_____________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________"
                , "" +
                             "___xx___o__________" +
                             "_______o___________" +
                             "______o____________" +
                             "_____o_____________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________"
                ,
                         Figure.ZERO,
                         NotOneFigureChangedException.class),
            Arguments.of("" +
                             "___xx______________" +
                             "___________________" +
                             "______o____________" +
                             "_____o_____________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________" +
                             "___________________"
                , "" +
                             "___xx___oo_________"
                ,
                         Figure.ZERO,
                         GameFieldIncorrectSizeException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("argsForInvalidTurns")
    void givenInvalidTurns_whenValidate_ThenThrowsException(String currGameField, String newGameField, Figure figure,
                                                            Class<ValidCheckerException> validCheckerException) {
        assertThrows(validCheckerException, () -> gameChecker.validate(currGameField, newGameField, figure));
    }
}