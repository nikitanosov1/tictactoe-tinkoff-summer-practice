package ru.tinkoff.tictactoe.client.exception;

public class BotResponseStatusCodeNotOkException extends RuntimeException {

    public BotResponseStatusCodeNotOkException() {
        super("Код ответа бота не 200");
    }
}
