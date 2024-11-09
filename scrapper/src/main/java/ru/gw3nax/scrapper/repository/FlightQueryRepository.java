package ru.gw3nax.scrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gw3nax.scrapper.entity.FlightQuery;

public interface FlightQueryRepository extends JpaRepository<FlightQuery, Long> {
}