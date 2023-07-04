package ru.tinkoff.tictactoe.session;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Figure {
    CROSS('x'),
    ZERO('o');

    private final Character name;

    Figure(Character name) {
        this.name = name;
    }

    @JsonValue
    public Character getName() {
        return name;
    }
}
