package ru.tinkoff.tictactoe.session.impl;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.model.SessionWithAllTurns;
import ru.tinkoff.tictactoe.session.model.SessionWithLastTurn;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;
import ru.tinkoff.tictactoe.turn.model.Turn;
import ru.tinkoff.tictactoe.turn.persistance.postgres.TurnEntity;

@Mapper(componentModel = "spring")
interface SessionEntityMapper {

    @Mapping(source = "turnEntities", target = "turns")
    SessionWithAllTurns toAllTurnsSession(SessionEntity sessionEntity);

    @Mapping(source = "turnEntities", target = "lastTurn", qualifiedByName = "getLastTurn")
    SessionWithLastTurn toLastTurnSession(SessionEntity sessionEntity);

    @Named("getLastTurn")
    default Turn getLastTurn(List<TurnEntity> turnEntities) {
        if (turnEntities.isEmpty()) {
            return null;
        }
        return this.fromTurnEntity(turnEntities.get(turnEntities.size() - 1));
    }

    Turn fromTurnEntity(TurnEntity turnEntity);

    Session toSession(SessionEntity sessionEntity);

    List<Session> toListOfSession(List<SessionEntity> sessionEntities);
}
