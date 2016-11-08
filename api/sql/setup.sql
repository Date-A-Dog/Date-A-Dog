DROP DATABASE IF EXISTS dadapidb;
CREATE DATABASE dadapidb;
GRANT ALL PRIVILEGES ON DATABASE dadapidb to dadapisvc;

\c dadapidb;

CREATE TABLE doggies (
  id bigint PRIMARY KEY, -- Petfinder Id
  dog jsonb
);
GRANT ALL PRIVILEGES ON TABLE doggies TO dadapisvc;

CREATE TABLE users (
  id bigint PRIMARY KEY, -- Facebook Id
  fname text,
  lname text,
  street text,
  city text,
  state char(2),
  zipcode text,
  phoneNumber text,
  shelterId text
);
GRANT ALL PRIVILEGES ON TABLE users TO dadapisvc;

CREATE TABLE requests (
  id bigserial ,
  dogId bigint ,
  userId bigint,
  shelterId text,  -- consider constraint check on shelterId
  epoch bigint,
  status char(1),
  PRIMARY KEY (id),
  FOREIGN KEY (dogId) REFERENCES doggies (id),
  FOREIGN KEY (userId) REFERENCES users (id)
);
GRANT ALL PRIVILEGES ON TABLE requests TO dadapisvc;

CREATE TABLE judged (
  uid bigint,
  dogId bigint,
  liked boolean,
  epoch bigint,
  PRIMARY KEY(uid, dogId),
  FOREIGN KEY (uid) REFERENCES users (id),
  FOREIGN KEY (dogId) REFERENCES doggies (id)
);
GRANT ALL PRIVILEGES ON TABLE judged TO dadapisvc;

CREATE INDEX user_requests ON requests (id ASC);
CREATE INDEX shelter_requests ON requests (shelterId ASC);
