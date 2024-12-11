CREATE TABLE users
(
    id          SERIAL PRIMARY KEY,
    user_id     VARCHAR(255) NOT NULL,
    client_name VARCHAR(255) NOT NULL
);
