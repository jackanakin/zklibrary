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
INSERT INTO users VALUES ('jardel', '{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', true); --fun123
INSERT INTO users VALUES ('jack', '{noop}teste123', true);
INSERT INTO authorities VALUES ('jack', 'USER');
INSERT INTO authorities VALUES ('jardel', 'USER');

--BUSINESS LOGIC
CREATE TABLE book (
  id         	SERIAL PRIMARY KEY,
  name	            TEXT NOT NULL,
  author	            TEXT NOT NULL,
  description	            TEXT NOT NULL
);

create table people(
	id         	SERIAL PRIMARY KEY,
	name TEXT not null,
	email TEXT not null,
	constraint fk_people_users foreign key(email) references users(username)
);
