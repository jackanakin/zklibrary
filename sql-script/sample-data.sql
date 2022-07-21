INSERT INTO users VALUES ('jardel', '{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K', true); --fun123
INSERT INTO users VALUES ('jack', '{noop}teste123', true);
INSERT INTO authorities VALUES ('jack', 'USER');
INSERT INTO authorities VALUES ('jardel', 'USER');


INSERT INTO book VALUES (default, '1808', null, null, null);
INSERT INTO book VALUES (default, '1822', null, null, null);
INSERT INTO book VALUES (default, '1889', null, null, null);
