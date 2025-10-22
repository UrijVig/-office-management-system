CREATE table if not exists roles (
    id serial primary key,
    role varchar(50) not null UNIQUE
);

create table if not exists employees (
    id serial primary key,
    username varchar(50) not null UNIQUE,
    password varchar(250) not null,
    name varchar(50),
    surname varchar(50),
    role_id bigint not null default 3,
    created_at timestamp default now(),
    updated_at timestamp,
    active boolean default true,
    deactivated_at timestamp,
    password_updated_at timestamp,
    constraint fk_role foreign key (role_id) references roles (id)
);
