package ru.tinkoff.tictactoe.session;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StateOfSession {
    private UUID id;
    private UUID firstBotId;
    private UUID secondBotId;
    private Integer turn;
    private String gameField;
    private Boolean isActive;
    private Date updatedAt;
    private Date createdAt;
}

