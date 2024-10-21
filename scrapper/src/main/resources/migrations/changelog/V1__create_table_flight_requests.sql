CREATE TABLE flight_requests
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    chat_id    BIGINT       NOT NULL,
    from_place VARCHAR(255) NOT NULL,
    to_place   VARCHAR(255) NOT NULL,
    from_date  DATE         NOT NULL,
    to_date    DATE,
    currency   VARCHAR(10)  NOT NULL,
    price      DOUBLE PRECISION,
    CONSTRAINT fk_flight_requests_user FOREIGN KEY (chat_id) REFERENCES users (chat_id)
);
