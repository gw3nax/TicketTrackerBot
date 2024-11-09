CREATE TABLE flight_queries
(
    id         SERIAL PRIMARY KEY,
    chat_id    BIGINT       NOT NULL,
    from_place VARCHAR(255) NOT NULL,
    to_place   VARCHAR(255) NOT NULL,
    from_date  DATE         NOT NULL,
    to_date    DATE,
    currency   VARCHAR(10)  NOT NULL,
    price      DECIMAL,
    CONSTRAINT fk_flight_queries_user FOREIGN KEY (chat_id) REFERENCES users (chat_id)
);