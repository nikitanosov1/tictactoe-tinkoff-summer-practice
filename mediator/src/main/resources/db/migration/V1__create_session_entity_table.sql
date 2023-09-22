create table session_entity
(
    status            varchar(20),
    created_at        timestamp(6),
    updated_at        timestamp(6),
    id                uuid not null,
    attacking_bot_url varchar(100),
    attacking_bot_id  varchar(30),
    defending_bot_url varchar(100),
    defending_bot_id  varchar(30),
    win_bot           varchar(30),
    primary key (id)
)