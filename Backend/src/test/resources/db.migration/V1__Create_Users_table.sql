CREATE TABLE if not exists users (
fname VARCHAR(255) NOT NULL,
lname VARCHAR(255) NOT NULL,
email VARCHAR(255) PRIMARY KEY,
password VARCHAR(50) NOT NULL
);