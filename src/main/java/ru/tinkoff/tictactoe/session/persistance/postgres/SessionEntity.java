package ru.tinkoff.tictactoe.session.persistance.postgres;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.tinkoff.tictactoe.turn.persistance.postgres.TurnEntity;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "session_entity")
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "first_bot_ip")
    private InetAddress firstBotIp;

    @Column(name = "first_bot_port")
    private Integer firstBotPort;

    @Column(name = "second_bot_ip")
    private InetAddress secondBotIp;

    @Column(name = "second_bot_port")
    private Integer secondBotPort;

    @OneToMany(mappedBy = "sessionEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TurnEntity> turnEntities = new ArrayList<>();

    @Column(name = "is_active")
    private Boolean isActive;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
}
