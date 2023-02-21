CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR NOT NULL,
  surname VARCHAR,
  email VARCHAR NOT NULL,
  dtc timestamp not null default current_timestamp,
  CONSTRAINT pk_user PRIMARY KEY (id),
  CONSTRAINT uq_name UNIQUE (name),
  CONSTRAINT uq_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS events (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  author_id BIGINT NOT NULL REFERENCES users (id),
  name VARCHAR NOT NULL,
  description_short TEXT NOT NULL,
  description_full TEXT,
  start timestamp not null,
  published timestamp not null default current_timestamp,
  paid boolean not null default false,
  moderation_required boolean not null default true,
  users_limit INT not null default 0,
  status VARCHAR not null default 'PENDING',
  views INT not null default 0,
  dtc timestamp not null default current_timestamp,
  dtu timestamp default null on update current_timestamp,
  CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS categories (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR NOT NULL,
  description VARCHAR,
  dtc timestamp not null default current_timestamp,
  dtu timestamp default null on update current_timestamp,
  CONSTRAINT pk_category PRIMARY KEY (id),
  CONSTRAINT uq_cat UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS collections (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR NOT NULL,
  description TEXT,
  show_on_main boolean not null default false,
  dtc timestamp not null default current_timestamp,
  dtu timestamp default null on update current_timestamp,
  CONSTRAINT pk_collection PRIMARY KEY (id),
  CONSTRAINT uq_coll UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS user_event (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  user_id BIGINT NOT NULL REFERENCES users (id),
  event_id BIGINT NOT NULL REFERENCES events (id),
  status VARCHAR not null default 'WAITING',
  dtc timestamp,
  dtu timestamp,
  CONSTRAINT pk_user_event PRIMARY KEY (id),
  CONSTRAINT uq_user_event UNIQUE (user_id, event_id)
);

CREATE TABLE IF NOT EXISTS event_category (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  category_id BIGINT NOT NULL REFERENCES categories (id),
  event_id BIGINT NOT NULL REFERENCES events (id),
  dtc timestamp not null default current_timestamp,
  dtu timestamp default null on update current_timestamp,
  CONSTRAINT pk_event_category PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS event_collection (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  collection_id BIGINT NOT NULL REFERENCES collections (id),
  event_id BIGINT NOT NULL REFERENCES events (id),
  dtc timestamp not null default current_timestamp,
  dtu timestamp default null on update current_timestamp,
  CONSTRAINT pk_event_collection PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS locations (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  lat float NOT NULL,
  lon float NOT NULL,
  description TEXT,
  dtc timestamp not null default current_timestamp,
  dtu timestamp default null on update current_timestamp,
  CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS event_location (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  event_id BIGINT NOT NULL REFERENCES events (id),
  location_id BIGINT NOT NULL REFERENCES locations (id),
  dtc timestamp not null default current_timestamp,
  dtu timestamp default null on update current_timestamp,
  CONSTRAINT pk_event_location PRIMARY KEY (id)
);

CREATE OR REPLACE ALIAS distance FOR "ru.prakticum.ewm.util.CustomSqlFunctions.getDistance";
