package ru.tinkoff.tictactoebottinkoffsummerpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.tictactoebottinkoffsummerpractice.config.BotConfig;

@SpringBootApplication
@EnableConfigurationProperties({
    BotConfig.class,
})
public class TictactoeBotTinkoffSummerPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TictactoeBotTinkoffSummerPracticeApplication.class, args);
    }

}
