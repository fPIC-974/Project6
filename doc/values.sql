USE test;

-- default list of users

INSERT INTO user (firstName, lastName, email, password, role) VALUES('user0', 'USER0', 'user0@mail.net', '$2a$10$TB/sjugjMNgx9nqXs2MHE.fzHcwHcawgtu3T3gTp52RKImuisqEtu', 'USER');
INSERT INTO user (firstName, lastName, email, password, role) VALUES('user1', 'USER1', 'user1@mail.net', '$2a$10$TB/sjugjMNgx9nqXs2MHE.fzHcwHcawgtu3T3gTp52RKImuisqEtu', 'USER');
INSERT INTO user (firstName, lastName, email, password, role) VALUES('user2', 'USER2', 'user2@mail.net', '$2a$10$TB/sjugjMNgx9nqXs2MHE.fzHcwHcawgtu3T3gTp52RKImuisqEtu', 'USER');
INSERT INTO user (firstName, lastName, email, password, role) VALUES('user3', 'USER3', 'user3@mail.net', '$2a$10$TB/sjugjMNgx9nqXs2MHE.fzHcwHcawgtu3T3gTp52RKImuisqEtu', 'USER');
INSERT INTO user (firstName, lastName, email, password, role) VALUES('user4', 'USER4', 'user4@mail.net', '$2a$10$TB/sjugjMNgx9nqXs2MHE.fzHcwHcawgtu3T3gTp52RKImuisqEtu', 'USER');
INSERT INTO user (firstName, lastName, email, password, role) VALUES('user5', 'USER5', 'user5@mail.net', '$2a$10$TB/sjugjMNgx9nqXs2MHE.fzHcwHcawgtu3T3gTp52RKImuisqEtu', 'USER');
INSERT INTO user (firstName, lastName, email, password, role) VALUES('user6', 'USER6', 'user6@mail.net', '$2a$10$TB/sjugjMNgx9nqXs2MHE.fzHcwHcawgtu3T3gTp52RKImuisqEtu', 'USER');
INSERT INTO user (firstName, lastName, email, password, role) VALUES('user7', 'USER7', 'user7@mail.net', '$2a$10$TB/sjugjMNgx9nqXs2MHE.fzHcwHcawgtu3T3gTp52RKImuisqEtu', 'USER');
INSERT INTO user (firstName, lastName, email, password, role) VALUES('user8', 'USER8', 'user8@mail.net', '$2a$10$TB/sjugjMNgx9nqXs2MHE.fzHcwHcawgtu3T3gTp52RKImuisqEtu', 'USER');
INSERT INTO user (firstName, lastName, email, password, role) VALUES('user9', 'USER9', 'user9@mail.net', '$2a$10$TB/sjugjMNgx9nqXs2MHE.fzHcwHcawgtu3T3gTp52RKImuisqEtu', 'USER');

-- create balance for users

INSERT INTO balance (id, amount) VALUES(1, 1000.00);
INSERT INTO balance (id, amount) VALUES(2, 1000.00);
INSERT INTO balance (id, amount) VALUES(3, 1000.00);
INSERT INTO balance (id, amount) VALUES(4, 1000.00);
INSERT INTO balance (id, amount) VALUES(5, 1000.00);
INSERT INTO balance (id, amount) VALUES(6, 1000.00);
INSERT INTO balance (id, amount) VALUES(7, 1000.00);
INSERT INTO balance (id, amount) VALUES(8, 1000.00);
INSERT INTO balance (id, amount) VALUES(9, 1000.00);
INSERT INTO balance (id, amount) VALUES(10,1000.00);

-- connect user0 with user1

INSERT INTO connection (user_id, connection_id) VALUES(1, 2);

-- add payment between user0 and user1

INSERT INTO payment (amount, description, user_id, balance_id) VALUES(10.00, 'transaction 1', 2, 1);
