package ru.tinkoff.tictactoebottinkoffsummerpractice.bot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.tictactoebottinkoffsummerpractice.bot.BotService;

@RestController
@RequiredArgsConstructor
public class BotController {
    private final BotService botService;

    @PostMapping("/bot/turn")
    public GameFieldResponseDto makeTurn(@RequestBody GameFieldRequestDto gameFieldRequestDto) {
        String gameField = gameFieldRequestDto.gameField();
        String newGameField = botService.makeTurnByGameField(gameField);
        return GameFieldResponseDto.builder().gameField(newGameField).build();
    }
}
