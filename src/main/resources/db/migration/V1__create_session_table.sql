create table session (
    id uuid not null,
    first_bot_id uuid,
    second_bot_id uuid,
    total_turns integer,
    is_active boolean,
    updated_at timestamp(6),
    created_at timestamp(6),
    primary key (id)
)