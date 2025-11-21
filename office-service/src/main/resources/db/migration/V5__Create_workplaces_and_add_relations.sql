create table if not exists workplaces(
    id serial primary key,
    name varchar(20) not null unique, 
    description varchar(255),
    created_at timestamp default now(),
    updated_at timestamp default null
);
insert into workplaces (name, description) values
('STORAGE', 'Склад или тестовые стенды');

alter table employees add column if not exists workplace_id bigint default null;
alter table employees add constraint fk_employee_workplace foreign key (workplace_id) references workplaces (id);

alter table equipments add column if not exists workplace_id bigint default 1;

alter table equipments add constraint fk_equipment_workplace foreign key (workplace_id) references workplaces (id);