create table session_history
(
    id         uuid not null,
    session_id uuid,
    turn       integer,
    game_field boolean,
    created_at timestamp(6),
    primary key (id)
)