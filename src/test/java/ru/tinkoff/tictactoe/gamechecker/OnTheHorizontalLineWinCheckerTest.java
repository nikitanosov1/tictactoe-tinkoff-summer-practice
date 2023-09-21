package ru.tinkoff.tictactoe.gamechecker;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.tinkoff.tictactoe.session.Figure;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class OnTheHorizontalLineWinCheckerTest {
    private final OnTheHorizontalLineWinChecker checker = new OnTheHorizontalLineWinChecker();

    @Test
    void givenWinningGameFieldForCross_whenCheck_thenReturnCorrectWinCheckerResults() {
        String gameField = "xxxxxoooo________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String expectGameField = "XXXXXoooo________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
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
        String gameField = "xxxx_oooo_ooooo__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
        String expectGameField = "xxxx_oooo_OOOOO__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________";
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
                Arguments.of("xxxx_oooo________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________",
                        Figure.ZERO),
                Arguments.of("_oxx_oooo___________xxx________________oooo______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________",
                        Figure.ZERO),
                Arguments.of("_____ooxo___xxx_____________xxxx______ooooo______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________",
                        Figure.CROSS),
                Arguments.of("_____ooxo___xxxxo___________xxxx______oooo_______________x___o___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________",
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
                Arguments.of("xxxx_oooo_ooooo__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________",
                        Figure.ZERO),
                Arguments.of("_oxx_oooo___________xxx________________ooooo______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________",
                        Figure.ZERO),
                Arguments.of("_____ooxo___xxx_____________xxxxx______ooooo______________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________",
                        Figure.CROSS),
                Arguments.of("_____ooxo___xxxxx___________xxxx______oooo_______________x___o___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________",
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