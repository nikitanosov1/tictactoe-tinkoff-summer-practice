create table turn_entity
(
    turn              integer,
    created_at        timestamp(6),
    id                uuid not null,
    session_entity_id uuid,
    game_field        varchar(361),
    primary key (id)
);

alter table if exists turn_entity
    add constraint FK5j3e0db1ni0xn4elcnleyh5io
        foreign key (session_entity_id)
            references session_entity