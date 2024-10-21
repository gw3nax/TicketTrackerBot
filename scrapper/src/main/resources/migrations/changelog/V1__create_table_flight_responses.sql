CREATE TABLE flight_responses
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    chat_id  BIGINT      NOT NULL,
    currency VARCHAR(10) NOT NULL,
    price    DOUBLE PRECISION,
    CONSTRAINT fk_flight_responses_user FOREIGN KEY (chat_id) REFERENCES users (chat_id)
);
