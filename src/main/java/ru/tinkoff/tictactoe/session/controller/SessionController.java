package ru.tinkoff.tictactoe.session.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.tictactoe.session.Figure;
import ru.tinkoff.tictactoe.session.model.Session;
import ru.tinkoff.tictactoe.session.SessionService;
import ru.tinkoff.tictactoe.turn.model.StateOfSession;

import java.util.List;
import java.util.UUID;

@RequestMapping("/sessions")
@RestController
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    private final SessionMapper sessionMapper;

    @PostMapping("")
    public CreateSessionResponseDto createSession() {
        Session session = sessionService.createSession();
        return sessionMapper.toCreateSessionResponseDto(session);
    }

    @GetMapping("")
    public List<FullStateOfSessionResponseDto> getSessions(@RequestParam(value = "isActive", required = false) Boolean isActive) {
        List<StateOfSession> stateOfSessions = sessionService.getSessionsByIsActive(isActive);
        return sessionMapper.toListFullStateOfSessionResponseDto(stateOfSessions);
    }

    @PostMapping("/{session_id}/registration")
    public RegisterBotResponseDto registerBotInSession(@PathVariable("session_id") UUID sessionId,
                                                       @RequestBody RegisterBotRequestDto registerBotRequestDto) {
        Figure figure = sessionService.registerBotInSession(sessionId, registerBotRequestDto.getBotId());
        return new RegisterBotResponseDto(figure);
    }

    @GetMapping("/{session_id}")
    public StateOfSessionResponseDto getCurrentStateOfSession(@PathVariable("session_id") UUID sessionId) {
        StateOfSession stateOfSession = sessionService.getCurrentStateOfSession(sessionId);
        return sessionMapper.toStateOfSessionResponseDto(stateOfSession);
    }

    @GetMapping("/{session_id}/turns/{turn}")
    public StateOfSessionResponseDto getCurrentStateOfSession(@PathVariable("session_id") UUID sessionId, @PathVariable("turn") Integer turn) {
        StateOfSession stateOfSession = sessionService.getStateOfSessionByTurn(sessionId, turn);
        return sessionMapper.toStateOfSessionResponseDto(stateOfSession);
    }
}
