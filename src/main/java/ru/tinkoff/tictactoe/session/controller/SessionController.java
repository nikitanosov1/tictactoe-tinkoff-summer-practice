package ru.tinkoff.tictactoe.session.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.SessionService;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.turn.TurnService;
import ru.tinkoff.tictactoe.turn.model.Turn;

import java.util.List;
import java.util.UUID;

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
                                                       @RequestBody RegisterBotRequestDto registerBotRequestDto) {
        Figure figure = sessionService.registerBotInSession(sessionId, registerBotRequestDto.getBotId());
        return new RegisterBotResponseDto(figure);
    }

    @GetMapping("/{session_id}")
    public SessionResponseDto getSession(@PathVariable("session_id") UUID sessionId) {
        Session session = sessionService.getSession(sessionId);
        return sessionMapper.toSessionResponseDto(session);
    }

    @GetMapping("/{session_id}/turns/{turn}")
    public TurnResponseDto getTurnBySessionIdAndTurn(@PathVariable("session_id") UUID sessionId, @PathVariable("turn") Integer turnPath) {
        Turn turn = turnService.getTurnBySessionIdAndTurn(sessionId, turnPath);
        return sessionMapper.toTurnResponseDto(turn);
    }
}
