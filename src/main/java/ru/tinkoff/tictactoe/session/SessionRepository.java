package ru.tinkoff.tictactoe.session;

import ru.tinkoff.tictactoe.session.model.Session;

public interface SessionRepository {
    Session createSession();
}
