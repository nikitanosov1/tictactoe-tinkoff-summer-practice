package ru.tinkoff.tictactoe.session.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.Session;
import ru.tinkoff.tictactoe.session.SessionRepository;
import ru.tinkoff.tictactoe.session.SessionService;
import ru.tinkoff.tictactoe.session.dto.FullStateOfSessionResponseDto;
import ru.tinkoff.tictactoe.session.dto.StateOfSessionResponseDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public Session createSession() {
        Session session = Session.builder()
                .isActive(false)
                .totalTurns(0)
                .build();
        return sessionRepository.save(session);
    }

    @Override
    public Figure registerBotInSession(UUID sessionId, UUID botId) {
        // TODO: реализовать регистрацию бота в сессии
        return Figure.ZERO;
    }

    @Override
    public StateOfSessionResponseDto getCurrentStateOfSession(UUID sessionId) {
        // TODO: реализовать получение текущего состояния сессии
        return StateOfSessionResponseDto.builder()
                .lastTurnTime(new Date())
                .gameField("_oo___oo_xoxo__xoo_o_oxx_oo_x______o_______x___oo_x_x_o_x_______oxo___o_x__o_o______x__o_____x__o_____o______x_____xoxoo___xo_____o__x_x__________x__x____o_xo__x__o___x_______o_x______xo______oxo_x_xx__xox___ox____x_oo__ox_x_x___o__________x______________o_____x____o___x___xo___x__x_xo__x_x___ox___x_______x____x_o_x__x_o__ox__o__x__ox_x_____x_oo_____x____o_ox")
                .build();
    }

    @Override
    public StateOfSessionResponseDto getStateOfSessionByTurn(UUID sessionId, Integer turn) {
        // TODO: реализовать получение состояния сессии по ее UUID в момент хода turn
        return StateOfSessionResponseDto.builder()
                .lastTurnTime(new Date())
                .gameField("_oo___oo_xoxo__xoo_o_oxx_oo_x______o_______x___oo_x_x_o_x_______oxo___o_x__o_o______x__o_____x__o_____o______x_____xoxoo___xo_____o__x_x__________x__x____o_xo__x__o___x_______o_x______xo______oxo_x_xx__xox___ox____x_oo__ox_x_x___o__________x______________o_____x____o___x___xo___x__x_xo__x_x___ox___x_______x____x_o_x__x_o__ox__o__x__ox_x_____x_oo_____x____o_ox")
                .build();
    }

    @Override
    public List<FullStateOfSessionResponseDto> getSessionsByIsActive(Boolean isActive) {
        // TODO: реализовать получение сессий по активности
        return null;
    }
}
