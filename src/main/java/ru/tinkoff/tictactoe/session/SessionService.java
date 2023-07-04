package ru.tinkoff.tictactoe.session;

import ru.tinkoff.tictactoe.session.dto.FullStateOfSessionResponseDto;
import ru.tinkoff.tictactoe.session.dto.StateOfSessionResponseDto;

import java.util.List;
import java.util.UUID;

public interface SessionService {
    Session createSession();

    Figure registerBotInSession(UUID sessionId, UUID botId);

    StateOfSessionResponseDto getCurrentStateOfSession(UUID sessionId);

    StateOfSessionResponseDto getStateOfSessionByTurn(UUID sessionId, Integer turn);

    List<FullStateOfSessionResponseDto> getSessionsByIsActive(Boolean isActive);
}
