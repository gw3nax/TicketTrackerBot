package ru.gw3nax.scrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gw3nax.scrapper.dto.request.FlightRequest;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.util.Optional;

@Repository
public interface FlightQueryRepository extends JpaRepository<FlightQuery, Long> {
    @Modifying
    @Query("DELETE FROM FlightQuery f WHERE f.userId = :#{#flightRequest.userId} " +
            "AND f.fromPlace = :#{#flightRequest.fromPlace} " +
            "AND f.toPlace = :#{#flightRequest.toPlace} " +
            "AND f.fromDate = :#{#flightRequest.fromDate} " +
            "AND f.toDate = :#{#flightRequest.toDate} " +
            "AND f.currency = :#{#flightRequest.currency} " +
            "AND f.price = :#{#flightRequest.price} " +
            "AND f.clientTopicName = :#{#header}")
    void deleteFlightQueryByFlightRequestAndHeader(@Param("flightRequest") FlightRequest flightRequest, @Param("header") String header);

    @Modifying
    @Query("""
    INSERT INTO FlightQuery (userId, fromPlace, toPlace, fromDate, toDate, currency, price, clientTopicName)
    SELECT :#{#flightRequest.userId}, :#{#flightRequest.fromPlace}, 
           :#{#flightRequest.toPlace}, :#{#flightRequest.fromDate}, :#{#flightRequest.toDate}, 
           :#{#flightRequest.currency}, :#{#flightRequest.price}, :#{#flightRequest.clientTopicName}
    WHERE NOT EXISTS (
        SELECT 1 FROM FlightQuery f 
        WHERE f.userId = :#{#flightRequest.userId} 
          AND f.fromPlace = :#{#flightRequest.fromPlace} 
          AND f.toPlace = :#{#flightRequest.toPlace} 
          AND f.fromDate = :#{#flightRequest.fromDate} 
          AND f.toDate = :#{#flightRequest.toDate} 
          AND f.currency = :#{#flightRequest.currency} 
          AND f.price = :#{#flightRequest.price}
          AND f.clientTopicName = :#{#flightRequest.clientTopicName}
    )
""")
    void saveIfNotExist(@Param("flightRequest") FlightQuery flightQuery);

    boolean existsFlightQueryByClientTopicName(String topicName);

    Optional<FlightQuery> findFlightQueryByClientTopicNameAndAndUserId(String topicName, String userId);
}
