package ru.tinkoff.tictactoe.gamechecker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.tinkoff.tictactoe.session.Figure;

class OnTheVerticalLineWinCheckerTest {

    private final OnTheVerticalLineWinChecker checker = new OnTheVerticalLineWinChecker();

    @Test
    void givenWinningGameFieldForCross_whenCheck_thenReturnCorrectWinCheckerResults() {
        String gameField = "" +
            "___________________" +
            "______________x____" +
            "______________x____" +
            "______________x____" +
            "_____x________x____" +
            "_____x_____________" +
            "_____x_____________" +
            "_____x_____________" +
            "_____x_____________" +
            "___________________" +
            "___________ooo_____" +
            "___________________" +
            "_________xxx_______" +
            "___________________" +
            "___________________" +
            "___________________" +
            "_____o_______x_____" +
            "___________________" +
            "___________________";
        String expectGameField = "" +
            "___________________" +
            "______________x____" +
            "______________x____" +
            "______________x____" +
            "_____X________x____" +
            "_____X_____________" +
            "_____X_____________" +
            "_____X_____________" +
            "_____X_____________" +
            "___________________" +
            "___________ooo_____" +
            "___________________" +
            "_________xxx_______" +
            "___________________" +
            "___________________" +
            "___________________" +
            "_____o_______x_____" +
            "___________________" +
            "___________________";
        Figure figure = Figure.CROSS;
        WinCheckerResults results = checker.check(gameField, figure);
        final var assertion = assertThat(results)
            .asInstanceOf(InstanceOfAssertFactories.type(GameWinResult.class));
        assertion
            .extracting(GameWinResult::win)
            .isEqualTo(true);
        assertion
            .extracting(GameWinResult::newGameField)
            .isEqualTo(expectGameField);
    }

    @Test
    void givenWinningGameFieldForZero_whenCheck_thenReturnCorrectWinCheckerResults() {
        String gameField = "" +
            "___________________" +
            "______________x____" +
            "______________x____" +
            "______________x____" +
            "_____o________x____" +
            "_____o_____________" +
            "_____o_____________" +
            "_____o_____________" +
            "_____o_____________" +
            "___________________" +
            "___________ooo_____" +
            "___________________" +
            "_________xxx_______" +
            "___________________" +
            "___________________" +
            "___________________" +
            "_____o_______x_____" +
            "___________________" +
            "___________________";
        String expectGameField = "" +
            "___________________" +
            "______________x____" +
            "______________x____" +
            "______________x____" +
            "_____O________x____" +
            "_____O_____________" +
            "_____O_____________" +
            "_____O_____________" +
            "_____O_____________" +
            "___________________" +
            "___________ooo_____" +
            "___________________" +
            "_________xxx_______" +
            "___________________" +
            "___________________" +
            "___________________" +
            "_____o_______x_____" +
            "___________________" +
            "___________________";
        Figure figure = Figure.ZERO;
        WinCheckerResults results = checker.check(gameField, figure);
        final var assertion = assertThat(results)
            .asInstanceOf(InstanceOfAssertFactories.type(GameWinResult.class));
        assertion
            .extracting(GameWinResult::win)
            .isEqualTo(true);
        assertion
            .extracting(GameWinResult::newGameField)
            .isEqualTo(expectGameField);
    }

    private static Stream<Arguments> argsForIsWinFalse() {
        return Stream.of(
            Arguments.of("" +
                             "___ooooo___________" +
                             "______________x____" +
                             "______________x____" +
                             "______________x____" +
                             "_____o________x____" +
                             "______o____________" +
                             "_______o___________" +
                             "________o__________" +
                             "_________o_________" +
                             "___________________" +
                             "________o__ooo_____" +
                             "_______o___________" +
                             "______o__xxx_______" +
                             "ooo__o___________oo" +
                             "____o______________" +
                             "o_____o_ooo___ooo_o" +
                             "_____o_______x_____" +
                             "___________________" +
                             "___________________"
                ,
                         Figure.ZERO),
            Arguments.of("" +
                             "___xxxxx___________" +
                             "______________x____" +
                             "______________x____" +
                             "______________x____" +
                             "_____x________x____" +
                             "______x____________" +
                             "_______x___________" +
                             "________x__________" +
                             "_________x_________" +
                             "___________________" +
                             "________x__ooo_____" +
                             "_______x___________" +
                             "______x__xxx_______" +
                             "ooo__x___________oo" +
                             "____x______________" +
                             "o_____o_ooo___ooo_o" +
                             "_____o_______x_____" +
                             "___________________" +
                             "___________________"
                ,
                         Figure.CROSS)
        );
    }

    @ParameterizedTest
    @MethodSource("argsForIsWinFalse")
    void givenLosingGameField_whenCheck_thenReturnIsWinFalse(String gameField, Figure figure) {
        WinCheckerResults results = checker.check(gameField, figure);
        assertThat(results.win()).isFalse();
    }

    private static Stream<Arguments> argsForIsWinTrue() {
        return Stream.of(
            Arguments.of("" +
                             "___oooo____________" +
                             "______________x____" +
                             "______________x____" +
                             "______________x____" +
                             "______________x____" +
                             "_o_________________" +
                             "_o_____x___________" +
                             "_o______x__________" +
                             "_o_______x_________" +
                             "_o_________________" +
                             "________o__ooo_____" +
                             "_______o___________" +
                             "_________xxx_______" +
                             "ooo__o___________oo" +
                             "____o______________" +
                             "o_____o_ooo___ooo_o" +
                             "_____o_______x_____" +
                             "___________________" +
                             "___________________"
                ,
                         Figure.ZERO),
            Arguments.of("" +
                             "___oooo____________" +
                             "______________x____" +
                             "______________x____" +
                             "______________x____" +
                             "______________x____" +
                             "_x_________________" +
                             "_x_____x___________" +
                             "_x______x__________" +
                             "_x_______x_________" +
                             "_x_________________" +
                             "________o__ooo_____" +
                             "_______o___________" +
                             "_________xxx_______" +
                             "ooo__o___________oo" +
                             "____o______________" +
                             "o_____o_ooo___ooo_o" +
                             "_____o_______x_____" +
                             "___________________" +
                             "___________________"
                ,
                         Figure.CROSS)
        );
    }

    @ParameterizedTest
    @MethodSource("argsForIsWinTrue")
    void givenLosingGameField_whenCheck_thenReturnIsWinTrue(String gameField, Figure figure) {
        WinCheckerResults results = checker.check(gameField, figure);
        assertThat(results.win()).isTrue();
    }
}