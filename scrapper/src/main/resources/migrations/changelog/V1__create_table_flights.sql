CREATE TABLE flights
(
    id                 SERIAL PRIMARY KEY,
    flight_response_id BIGINT       NOT NULL,
    from_place         VARCHAR(255) NOT NULL,
    to_place           VARCHAR(255) NOT NULL,
    from_date          DATE         NOT NULL,
    to_date            DATE,
    link               TEXT,
    CONSTRAINT fk_flights_flight_response FOREIGN KEY (flight_response_id) REFERENCES flight_responses (id) ON DELETE CASCADE
);

