package ru.tinkoff.tictactoe;

import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public abstract class ApiException extends RuntimeException implements ErrorResponse {

    public ApiException(String message) {
        super(message);
    }

    @Override
    public ProblemDetail getBody() {
        return ProblemDetail.forStatusAndDetail(
            getStatusCode(),
            getMessage()
        );
    }
}
