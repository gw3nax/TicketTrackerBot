package ru.gw3nax.scrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gw3nax.scrapper.entity.Flight;
import ru.gw3nax.scrapper.entity.FlightResponse;

@Repository
public interface FlightResponseRepository extends JpaRepository<FlightResponse, Long> {
}
