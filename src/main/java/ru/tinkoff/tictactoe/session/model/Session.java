package ru.tinkoff.tictactoe.session.model;

import lombok.*;
import ru.tinkoff.tictactoe.turn.model.Turn;
import ru.tinkoff.tictactoe.turn.persistance.postgres.TurnEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Session {
    private UUID id;
    private UUID firstBotId;
    private UUID secondBotId;
    private Integer totalTurns;
    private List<Turn> turns;
    private Boolean isActive;
    private Date updatedAt;
    private Date createdAt;
}
