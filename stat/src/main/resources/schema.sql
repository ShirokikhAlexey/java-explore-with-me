CREATE TABLE IF NOT EXISTS events (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  app VARCHAR(255) NOT NULL,
  uri VARCHAR(512) NOT NULL,
  ip VARCHAR(512) NOT NULL,
  `timestamp` timestamp not null default current_timestamp,
  CONSTRAINT pk_event PRIMARY KEY (id)
);