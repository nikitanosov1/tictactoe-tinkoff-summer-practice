package ru.tinkoff.tictactoe.session.model;

import jakarta.persistence.Column;
import lombok.*;
import ru.tinkoff.tictactoe.turn.model.Turn;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Session {
    private UUID id;
    private InetAddress firstBotIp;
    private Integer firstBotPort;
    private InetAddress secondBotIp;
    private Integer secondBotPort;
    private List<Turn> turns;
    private Boolean isActive;
    private Date updatedAt;
    private Date createdAt;

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", firstBotIp=" + firstBotIp +
                ", firstBotPort=" + firstBotPort +
                ", secondBotIp=" + secondBotIp +
                ", secondBotPort=" + secondBotPort +
                ", turns=" + turns +
                ", isActive=" + isActive +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
