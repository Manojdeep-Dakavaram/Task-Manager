CREATE TABLE if not exists tasks (
id  BIGSERIAL PRIMARY KEY,
title VARCHAR(255) NOT NULL,
description TEXT,
status VARCHAR(50) NOT NULL,
due_date DATE,
priority VARCHAR(50) NOT NULL,
email VARCHAR(50) NOT NULL,
FOREIGN KEY (email) REFERENCES users(email)
);
