package ru.tinkoff.tictactoebottinkoffsummerpractice.bot.impl;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import ru.tinkoff.tictactoebottinkoffsummerpractice.bot.BotService;
import ru.tinkoff.tictactoebottinkoffsummerpractice.bot.Figure;
import ru.tinkoff.tictactoebottinkoffsummerpractice.bot.registration.BotRegistrationService;
import ru.tinkoff.tictactoebottinkoffsummerpractice.config.BotConfig;

@Service
public class StupidBotService implements BotService {

    private final Figure figure;
    private final int fieldFlattenSize;

    public StupidBotService(
        BotRegistrationService registrationService,
        BotConfig botConfig
    ) {
        int gameFieldSize = botConfig.gameFieldSize();
        fieldFlattenSize = gameFieldSize * gameFieldSize;
        figure = registrationService.getFigure();
    }

    @Override
    public String makeTurnByGameField(String gameField) {
        final var newTurnIdx = generateNewRandomTurnIdx(gameField);
        final var builder = new StringBuilder(gameField);
        builder.replace(newTurnIdx, newTurnIdx + 1, figure.getName());
        return builder.toString();
    }

    int generateNewRandomTurnIdx(String gameField) {
        return Stream.generate(() -> ThreadLocalRandom.current().nextInt(0, fieldFlattenSize))
            .dropWhile(turnIdx -> {
                final var currentValue = gameField.substring(turnIdx, turnIdx + 1);
                return !Figure.EMPTY.getName().equals(currentValue);
            })
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("There is no field to make new turn"));
    }
}
