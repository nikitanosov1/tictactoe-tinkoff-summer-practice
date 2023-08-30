create table session_entity
(
    first_bot_port  integer,
    is_active       boolean,
    second_bot_port integer,
    created_at      timestamp(6),
    updated_at      timestamp(6),
    id              uuid not null,
    first_bot_ip    inet,
    second_bot_ip   inet,
    primary key (id)
)