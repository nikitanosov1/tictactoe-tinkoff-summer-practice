package ru.tinkoff.tictactoe.session.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.tictactoe.session.SessionService;

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
        return sessionMapper.toCreateSessionResponseDto(sessionService.createSession());
    }

    @GetMapping("")
    public List<FullStateOfSessionResponseDto> getSessions(@RequestParam(value = "isActive", required = false) Boolean isActive) {
        return sessionMapper.toListFullStateOfSessionResponseDto(sessionService.getSessionsByIsActive(isActive));
    }

    @PostMapping("/{session_id}/registration")
    public RegisterBotResponseDto registerBotInSession(@PathVariable("session_id") UUID sessionId,
                                                       @RequestBody RegisterBotRequestDto registerBotRequestDto) {
        return new RegisterBotResponseDto(sessionService.registerBotInSession(sessionId, registerBotRequestDto.getBotId()));
    }

    @GetMapping("/{session_id}")
    public StateOfSessionResponseDto getCurrentStateOfSession(@PathVariable("session_id") UUID sessionId) {
        return sessionMapper.toStateOfSessionResponseDto(sessionService.getCurrentStateOfSession(sessionId));
    }

    @GetMapping("/{session_id}/turns/{turn}")
    public StateOfSessionResponseDto getCurrentStateOfSession(@PathVariable("session_id") UUID sessionId, @PathVariable("turn") Integer turn) {
        return sessionMapper.toStateOfSessionResponseDto(sessionService.getStateOfSessionByTurn(sessionId, turn));
    }
}
