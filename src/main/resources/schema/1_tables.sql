create table locations
(
    id          serial primary key,
    name        text             not null,
    latitude    double precision not null,
    longitude   double precision not null,
    radius_m    int              not null,
    description text             not null default ''
);


create table poi
(
    id          serial primary key,
    name        text             not null,
    exp_points  int              not null default 0,
    location_id int              not null references locations (id),
    latitude    double precision not null,
    longitude   double precision not null,
    radius_m    int              not null
);

create table users
(

    id         bigint primary key,
    first_name text   not null,
    last_name  text,
    chat_id    int    not null
);


create table user_path
(

    id        serial primary key,
    user_id   int references users (id),
    latitude  double precision not null,
    longitude double precision not null
);

-- Таблица: locations
COMMENT ON TABLE locations IS 'Таблица географических локаций с центром и радиусом действия';
COMMENT ON COLUMN locations.id IS 'Уникальный идентификатор локации';
COMMENT ON COLUMN locations.name IS 'Название локации';
COMMENT ON COLUMN locations.latitude IS 'Широта центра локации (в градусах)';
COMMENT ON COLUMN locations.longitude IS 'Долгота центра локации (в градусах)';
COMMENT ON COLUMN locations.radius_m IS 'Радиус действия локации в метрах';
COMMENT ON COLUMN locations.description IS 'Описание локации';

-- Таблица: poi (Points of Interest)
COMMENT ON TABLE poi IS 'Таблица точек интереса (POI), привязанных к локациям';
COMMENT ON COLUMN poi.id IS 'Уникальный идентификатор точки интереса';
COMMENT ON COLUMN poi.name IS 'Название точки интереса';
COMMENT ON COLUMN poi.exp_points IS 'Количество очков опыта, начисляемых за посещение';
COMMENT ON COLUMN poi.location_id IS 'Внешний ключ на таблицу locations';
COMMENT ON COLUMN poi.latitude IS 'Широта центра точки интереса (в градусах)';
COMMENT ON COLUMN poi.longitude IS 'Долгота центра точки интереса (в градусах)';
COMMENT ON COLUMN poi.radius_m IS 'Радиус действия точки интереса в метрах';

-- Таблица: users
COMMENT ON TABLE users IS 'Таблица пользователей системы';
COMMENT ON COLUMN users.id IS 'Уникальный идентификатор пользователя (например, Telegram user ID)';
COMMENT ON COLUMN users.first_name IS 'Имя пользователя';
COMMENT ON COLUMN users.last_name IS 'Фамилия пользователя (может отсутствовать)';
COMMENT ON COLUMN users.chat_id IS 'ID чата пользователя в мессенджере (например, Telegram)';

-- Таблица: user_path
COMMENT ON TABLE user_path IS 'Таблица географических точек, посещённых пользователями (трекинг перемещений)';
COMMENT ON COLUMN user_path.id IS 'Уникальный идентификатор записи о перемещении';
COMMENT ON COLUMN user_path.user_id IS 'Внешний ключ на таблицу users';
COMMENT ON COLUMN user_path.latitude IS 'Широта точки пути пользователя (в градусах)';
COMMENT ON COLUMN user_path.longitude IS 'Долгота точки пути пользователя (в градусах)';