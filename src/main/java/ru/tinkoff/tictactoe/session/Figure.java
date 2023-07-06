package ru.tinkoff.tictactoe.session;

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
}
