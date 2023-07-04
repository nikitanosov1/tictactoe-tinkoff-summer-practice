package ru.tinkoff.tictactoe.session;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.tictactoe.session.dto.CreateSessionResponseDto;
import ru.tinkoff.tictactoe.session.dto.RegisterBotRequestDto;
import ru.tinkoff.tictactoe.session.dto.RegisterBotResponseDto;

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

    @PutMapping("/{session_id}")
    public RegisterBotResponseDto registerBotInSession(@PathVariable("session_id") UUID sessionId,
                                                       @RequestBody RegisterBotRequestDto registerBotRequestDto) {
        return new RegisterBotResponseDto(sessionService.registerBotInSession(sessionId, registerBotRequestDto.getBotId()));
    }
}
