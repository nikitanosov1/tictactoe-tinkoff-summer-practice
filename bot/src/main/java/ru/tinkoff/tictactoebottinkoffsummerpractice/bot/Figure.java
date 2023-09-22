package ru.tinkoff.tictactoebottinkoffsummerpractice.bot;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Figure {
    CROSS("x"),
    ZERO("o"),
    CROSSED_OUT_CROSS("X"),
    CROSSED_OUT_ZERO("O"),
    EMPTY("_"),
    ;

    private final String name;

    Figure(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public static Figure fromString(String text) {
        for (Figure figure : Figure.values()) {
            if (figure.name.equals(text)) {
                return figure;
            }
        }
        throw new UnsupportedOperationException();
    }

    public static Figure getCrossOutFigure(Figure figure) {
        if (figure.equals(Figure.CROSS)) {
            return Figure.CROSSED_OUT_CROSS;
        }
        if (figure.equals(Figure.ZERO)) {
            return Figure.CROSSED_OUT_ZERO;
        }
        throw new UnsupportedOperationException();
    }

    public static Figure getOppositeFigure(Figure figure) {
        if (figure.equals(Figure.CROSS)) {
            return Figure.ZERO;
        }
        if (figure.equals(Figure.ZERO)) {
            return Figure.CROSS;
        }
        throw new UnsupportedOperationException();
    }
}