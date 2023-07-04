package ru.tinkoff.tictactoe.session;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.tictactoe.session.dto.*;

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
        return sessionService.getSessionsByIsActive(isActive);
    }

    @PutMapping("/{session_id}")
    public RegisterBotResponseDto registerBotInSession(@PathVariable("session_id") UUID sessionId,
                                                       @RequestBody RegisterBotRequestDto registerBotRequestDto) {
        return new RegisterBotResponseDto(sessionService.registerBotInSession(sessionId, registerBotRequestDto.getBotId()));
    }

    @GetMapping("/{session_id}")
    public StateOfSessionResponseDto getCurrentStateOfSession(@PathVariable("session_id") UUID sessionId) {
        return sessionService.getCurrentStateOfSession(sessionId);
    }

    @GetMapping("/{session_id}/turn/{turn}")
    public StateOfSessionResponseDto getCurrentStateOfSession(@PathVariable("session_id") UUID sessionId, @PathVariable("turn") Integer turn) {
        return sessionService.getStateOfSessionByTurn(sessionId, turn);
    }
}
