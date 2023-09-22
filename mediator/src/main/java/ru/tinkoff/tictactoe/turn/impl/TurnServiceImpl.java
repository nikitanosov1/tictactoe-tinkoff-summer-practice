package ru.tinkoff.tictactoe.turn.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.tictactoe.turn.TurnRepository;
import ru.tinkoff.tictactoe.turn.TurnService;
import ru.tinkoff.tictactoe.turn.model.Turn;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TurnServiceImpl implements TurnService {
    private final TurnRepository turnRepository;

    @Override
    public Turn getTurnBySessionIdAndTurn(UUID sessionId, Integer turn) {
        return turnRepository.findTurnBySessionIdAndTurn(sessionId, turn);
    }
}
