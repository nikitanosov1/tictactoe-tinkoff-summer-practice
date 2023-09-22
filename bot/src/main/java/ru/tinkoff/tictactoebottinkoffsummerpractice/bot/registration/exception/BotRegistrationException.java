package ru.tinkoff.tictactoebottinkoffsummerpractice.bot.registration.exception;

public class BotRegistrationException extends RuntimeException {
    public BotRegistrationException() {
        super("Ошибка регистрации бота в сессии");
    }
}
