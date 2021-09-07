CREATE TABLE login.user (
     id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
     username CHAR(30) NOT NULL,
     password CHAR(30) NOT NULL
     
);

INSERT INTO login.user (username,password) VALUES
    ('admin','admin'), ('quocan','123');

SELECT * FROM login.user;