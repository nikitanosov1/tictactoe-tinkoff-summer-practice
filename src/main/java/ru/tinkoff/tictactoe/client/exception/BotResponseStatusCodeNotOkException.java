package ru.tinkoff.tictactoe.client.exception;

import ru.tinkoff.tictactoe.ApiException;

public class BotResponseStatusCodeNotOkException extends ApiException {
    public BotResponseStatusCodeNotOkException() {
        super("Код ответа бота не 200");
    }
}
