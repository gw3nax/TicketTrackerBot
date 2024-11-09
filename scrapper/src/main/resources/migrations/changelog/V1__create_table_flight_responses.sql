CREATE TABLE flight_responses
(
    id       SERIAL PRIMARY KEY,
    chat_id  BIGINT      NOT NULL,
    currency VARCHAR(10) NOT NULL,
    price    DECIMAL,
    CONSTRAINT fk_flight_responses_user FOREIGN KEY (chat_id) REFERENCES users (chat_id)
);
