package ru.tinkoff.tictactoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.tictactoe.session.impl.GameConfigs;

@SpringBootApplication
@EnableConfigurationProperties({
    GameConfigs.class,
})
public class TictactoeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TictactoeApplication.class, args);
    }

}
