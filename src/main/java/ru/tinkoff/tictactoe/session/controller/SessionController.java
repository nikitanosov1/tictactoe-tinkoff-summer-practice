package ru.tinkoff.tictactoe.session.controller;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.SessionService;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.model.SessionWithLastTurn;
import ru.tinkoff.tictactoe.turn.TurnService;
import ru.tinkoff.tictactoe.turn.model.Turn;

@RequestMapping("/sessions")
@RestController
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final TurnService turnService;
    private final SessionMapper sessionMapper;

    @PostMapping("")
    public CreateSessionResponseDto createSession() {
        Session session = sessionService.createSession();
        return sessionMapper.toCreateSessionResponseDto(session);
    }

    @GetMapping("")
    public List<SessionResponseDto> getSessions(@RequestParam(value = "isActive", required = false) Boolean isActive) {
        List<Session> sessions = sessionService.getSessionsByIsActive(isActive);
        return sessionMapper.toListSessionResponseDto(sessions);
    }

    @PostMapping("/{session_id}/registration")
    public RegisterBotResponseDto registerBotInSession(@PathVariable("session_id") UUID sessionId,
                                                       @RequestBody RegisterBotRequestDto registerBotRequestDto)
        throws InterruptedException {
        Figure figure = sessionService.registerBotInSession(
            sessionId,
            registerBotRequestDto.botUrl(),
            registerBotRequestDto.botId()
        );
        return new RegisterBotResponseDto(figure.getName());
    }

    @GetMapping("/{session_id}")
    public SessionLastTurnResponseDto getSession(@PathVariable("session_id") UUID sessionId) {
        SessionWithLastTurn session = sessionService.getSession(sessionId);
        return sessionMapper.toSessionResponseDto(session);
    }

    @GetMapping("/{session_id}/turns/{turn}")
    public TurnResponseDto getTurnBySessionIdAndTurn(
        @PathVariable("session_id") UUID sessionId,
        @PathVariable("turn") Integer turnPath
    ) {
        Turn turn = turnService.getTurnBySessionIdAndTurn(sessionId, turnPath);
        return sessionMapper.toTurnResponseDto(turn);
    }
}
