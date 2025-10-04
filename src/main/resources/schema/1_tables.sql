create table users
(
    id         bigint primary key not null,
    first_name text               not null
);


create table POIs
(
    id          serial primary key not null,
    title       text               not null,
    points      int                not null default 0,
    quote       text               not null,
    description text               not null,
    imageUrl    text               not null,
    latitude    double precision   not null,
    longitude   double precision   not null,
    radius_m    int                         default 20
);

create table user_POIs
(
    user_id     bigint not null,
    location_id int    not null,
    status      text   not null,
    primary key (user_id, location_id)
);