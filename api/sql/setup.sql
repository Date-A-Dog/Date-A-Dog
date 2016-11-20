-- DROP DATABASE IF EXISTS dateadog;
\c dateadog;
DROP TABLE doggies CASCADE;
DROP TABLE shelters CASCADE;
DROP TABLE users CASCADE;
DROP TABLE requests CASCADE;
DROP TABLE judged CASCADE;
DROP INDEX user_requests CASCADE;
DROP INDEX shelter_requests CASCADE;
-- CREATE DATABASE dateadog;
GRANT ALL PRIVILEGES ON DATABASE dateadog to dadadmin;

\c dateadog;

CREATE TABLE doggies (
  id bigint PRIMARY KEY, -- Petfinder Id
  dog jsonb
);
GRANT ALL PRIVILEGES ON TABLE doggies TO dadadmin;

CREATE TABLE shelters (
  id text PRIMARY KEY,
  shelter jsonb
);
GRANT ALL PRIVILEGES ON TABLE shelters TO dadadmin;

CREATE TABLE users (
  id bigint PRIMARY KEY, -- Facebook Id
  email text,
  fname text,
  lname text,
  street text,
  city text,
  state char(2),
  zip text,
  phone text,
  shelterId text DEFAULT NULL
);
GRANT ALL PRIVILEGES ON TABLE users TO dadadmin;

CREATE TABLE requests (
  id bigserial,
  dogId bigint,
  userId bigint,
  shelterId text,  -- consider constraint check on shelterId
  epoch bigint NOT NULL,
  status char(1) DEFAULT 'P',
  PRIMARY KEY (id),
  FOREIGN KEY (dogId) REFERENCES doggies (id),
  FOREIGN KEY (userId) REFERENCES users (id),
  UNIQUE (dogId, userId, epoch)
);
GRANT ALL PRIVILEGES ON TABLE requests TO dadadmin;

CREATE TABLE judged (
  userId bigint,
  dogId bigint,
  epoch bigint NOT NULL,
  liked boolean DEFAULT null,
  PRIMARY KEY(userId, dogId),
  FOREIGN KEY (userId) REFERENCES users (id),
  FOREIGN KEY (dogId) REFERENCES doggies (id)
);
GRANT ALL PRIVILEGES ON TABLE judged TO dadadmin;

CREATE INDEX user_requests ON requests (id ASC);
CREATE INDEX shelter_requests ON requests (shelterId ASC);
