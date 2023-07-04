package ru.tinkoff.tictactoe.session;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.tictactoe.session.dto.CreateSessionResponseDto;

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
}
