create table if not exists employees (
    id serial primary key,
    login varchar(20),
    password varchar(100) not null,
    name varchar(20) ,
    surname varchar(20) ,
    role varchar(3)
);
