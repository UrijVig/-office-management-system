create table if not exists equipment_types (
    id serial primary key,
    type varchar(50) not null UNIQUE,
    service_frequency varchar(20),
    service_life varchar(20)
);

create table if not exists equipment_statuses (
    id serial primary key,
    status varchar(50) not null UNIQUE
);

create table if not exists equipment_locations (
    id serial primary key,
    location varchar(50) not null UNIQUE
);

create table if not exists equipments (
    id serial primary key,
    serial_number varchar(50) not null UNIQUE,
    type_id bigint,
    name varchar (50),
    brand varchar (50),
    model varchar (50),
    description varchar (255),
    size numeric(5,2),
    location_id bigint not null default 1,
    status_id bigint not null default 1,
    created_at timestamp default now(),
    updated_at timestamp default null,
    served_at timestamp,
    price numeric(10,2),

    constraint fk_equipment_type foreign key (type_id) references equipment_types (id),
    constraint fk_equipment_location foreign key (location_id) references equipment_locations (id),
    constraint fk_equipment_status foreign key (status_id) references equipment_statuses (id)
);