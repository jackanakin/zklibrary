--SPRING SECURITY
create table users(
    id         	SERIAL PRIMARY KEY,
	username TEXT not null unique,
	password TEXT not null,
	enabled boolean not null
);

create table authorities (
	id         	SERIAL PRIMARY KEY,
	username TEXT not null unique,
	authority TEXT not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);

--BUSINESS LOGIC
create table person(
	id         	SERIAL PRIMARY KEY,
	name TEXT not null,
	user_id INT not null,
	constraint fk_person_users foreign key(user_id) references users(id)
);

CREATE TABLE book (
  id         	SERIAL PRIMARY KEY,
  name	            TEXT NOT NULL,
  code       TEXT DEFAULT NULL,
  booked     INT DEFAULT 0,
  booked_person_id         	INT NULL,
  constraint fk_book_person foreign key(booked_person_id) references person(id)
);
