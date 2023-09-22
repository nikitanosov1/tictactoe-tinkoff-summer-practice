package ru.tinkoff.tictactoe.session.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import ru.tinkoff.tictactoe.turn.model.Turn;

@Builder
public record SessionWithAllTurns(
    UUID id,
    String attackingBotUrl,
    String attackingBotId,
    String defendingBotUrl,
    String defendingBotId,
    List<Turn> turns,
    SessionStatus status,
    Date updatedAt,
    Date createdAt
) {}
