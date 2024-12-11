package ru.gw3nax.scrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.util.Optional;

@Repository
public interface FlightQueryRepository extends JpaRepository<FlightQuery, Long> {

    void deleteFlightQueryByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("""
        INSERT INTO FlightQuery (user, fromPlace, toPlace, fromDate, toDate, currency, price)
        SELECT :#{#flightQuery.user}, :#{#flightQuery.fromPlace}, :#{#flightQuery.toPlace}, 
               :#{#flightQuery.fromDate}, :#{#flightQuery.toDate}, :#{#flightQuery.currency}, 
               :#{#flightQuery.price}
        WHERE NOT EXISTS (
            SELECT 1 FROM FlightQuery f 
            WHERE f.user.id = :#{#flightQuery.user.id}
              AND f.fromPlace = :#{#flightQuery.fromPlace}
              AND f.toPlace = :#{#flightQuery.toPlace}
              AND f.fromDate = :#{#flightQuery.fromDate}
              AND f.toDate = :#{#flightQuery.toDate}
              AND f.currency = :#{#flightQuery.currency}
              AND f.price = :#{#flightQuery.price}
        )
        """)
    void saveIfNotExist(@Param("flightQuery") FlightQuery flightQuery);

    Optional<FlightQuery> findFlightQueryByUserId(Long userId);
}
