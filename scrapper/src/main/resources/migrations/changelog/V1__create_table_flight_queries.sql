CREATE TABLE flight_queries
(
    id         SERIAL PRIMARY KEY,
    user_id    VARCHAR(255) NOT NULL,
    from_place VARCHAR(255) NOT NULL,
    to_place   VARCHAR(255) NOT NULL,
    from_date  DATE         NOT NULL,
    to_date    DATE,
    currency   VARCHAR(10)  NOT NULL,
    price      DECIMAL
);
