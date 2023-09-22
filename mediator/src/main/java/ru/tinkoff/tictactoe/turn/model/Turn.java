package ru.tinkoff.tictactoe.turn.model;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Turn {
    private UUID id;
    private Integer turn;
    private String gameField;
    private Date createdAt;
}
