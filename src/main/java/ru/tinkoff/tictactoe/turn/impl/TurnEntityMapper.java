package ru.tinkoff.tictactoe.turn.impl;

import org.mapstruct.Mapper;
import ru.tinkoff.tictactoe.turn.model.Turn;
import ru.tinkoff.tictactoe.turn.persistance.postgres.TurnEntity;

@Mapper(componentModel = "spring")
interface TurnEntityMapper {
    Turn toTurn(TurnEntity turnEntity);
}
