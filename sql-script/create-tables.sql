--SPRING SECURITY
create table users(
	username TEXT not null primary key,
	password TEXT not null,
	enabled boolean not null
);
create table authorities (
	username TEXT not null,
	authority TEXT not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);

--BUSINESS LOGIC
create table person(
	id         	SERIAL PRIMARY KEY,
	name TEXT not null,
	email TEXT not null,
	constraint fk_person_users foreign key(email) references users(username)
);

CREATE TABLE book (
  id         	SERIAL PRIMARY KEY,
  name	            TEXT NOT NULL,
  remote_code       TEXT DEFAULT NULL,
  remote_booked     INT DEFAULT NULL,
  booked_person         	INT NULL,
  constraint fk_book_person foreign key(booked_person) references person(id)
);
