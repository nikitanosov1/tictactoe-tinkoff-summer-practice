package ru.tinkoff.tictactoe.turn.persistance.postgres;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.tinkoff.tictactoe.session.persistance.postgres.SessionEntity;

import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "turn_entity")
public class TurnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "turn")
    private Integer turn;

    @Column(name = "game_field", length = 361)
    private String gameField;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "session_entity_id")
    private SessionEntity sessionEntity;

    @Override
    public String toString() {
        return "TurnEntity{" +
                "id=" + id +
                ", turn=" + turn +
                ", gameField='" + gameField + '\'' +
                ", createdAt=" + createdAt +
                ", sessionEntity=" + sessionEntity +
                '}';
    }
}
