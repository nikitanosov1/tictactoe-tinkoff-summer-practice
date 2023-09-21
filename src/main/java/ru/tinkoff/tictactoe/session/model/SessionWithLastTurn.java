package ru.tinkoff.tictactoe.session.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import ru.tinkoff.tictactoe.turn.model.Turn;

@Builder
public record SessionWithLastTurn(
    UUID id,
    String attackingBotUrl,
    String attackingBotId,
    String defendingBotUrl,
    String defendingBotId,
    Turn lastTurn,
    Boolean isActive,
    Date updatedAt,
    Date createdAt
) {}
