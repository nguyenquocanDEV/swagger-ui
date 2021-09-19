CREATE
database  login;
use
login;

CREATE TABLE login.user
(

    username CHAR(30) NOT NULL PRIMARY KEY,
    password CHAR(100) NOT NULL

);

INSERT INTO login.user (username, password)
VALUES ('admin', 'admin'),
       ('quocan', '123');

SELECT *
FROM login.user;