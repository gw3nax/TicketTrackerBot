package ru.gw3nax.scrapper.processor;

import org.junit.jupiter.api.Test;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FlightProcessorTest {

    @Test
    void testFlightProcessorChain() {
        var processorChain = new PriceFlightProcessor(
                new DateFlightProcessor(
                        new AirlineFlightProcessor(null)
                )
        );

        var flights = List.of(
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 12,12,0), new BigDecimal(3000), "SU", "/example"),
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 13,12,0), new BigDecimal(4000), "N4", "/example"),
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 14,12,0), new BigDecimal(5000), "5N", "/example"),
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 15,12,0), new BigDecimal(6000), "S7", "/example")
        );

        var query = FlightQuery.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .fromDate(LocalDate.of(2024, 12, 13))
                .toDate(LocalDate.of(2024, 12, 14))
                .currency("RUB")
                .price(BigDecimal.valueOf(4500))
                .build();

        var filteredFlights = processorChain.process(flights, query);

        assertThat(filteredFlights).hasSize(1);

        var flight1 = filteredFlights.getFirst();

        assertThat(flight1.getAirline()).isEqualTo("N4");
        assertThat(flight1.getPrice()).isLessThanOrEqualTo(BigDecimal.valueOf(4000));
        assertThat(flight1.getDepartureAt()).isBetween(
                query.getFromDate().atStartOfDay(),
                query.getToDate().atStartOfDay()
        );
    }

    @Test
    void testPriceProcessorWithoutPriceLimit() {
        var processor = new PriceFlightProcessor(null);

        var flights = List.of(
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 12,12,0), new BigDecimal(3000), "SU", "/example"),
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 13,12,0), new BigDecimal(4000), "N4", "/example")
        );

        var query = FlightQuery.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .fromDate(LocalDate.of(2024, 12, 12))
                .toDate(LocalDate.of(2024, 12, 13))
                .currency("RUB")
                .price(null)
                .build();

        var filteredFlights = processor.process(flights, query);

        assertThat(filteredFlights).hasSize(2);
        assertThat(filteredFlights).isEqualTo(flights);
    }

    @Test
    void testDateProcessorWithoutDateLimit() {
        var processor = new DateFlightProcessor(null);

        var flights = List.of(
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 12,12,0), new BigDecimal(3000), "SU", "/example"),
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 13,12,0), new BigDecimal(4000), "N4", "/example")
        );

        var query = FlightQuery.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .fromDate(LocalDate.of(2024, 12, 12))
                .toDate(null)
                .currency("RUB")
                .price(BigDecimal.valueOf(4500))
                .build();

        var filteredFlights = processor.process(flights, query);

        assertThat(filteredFlights).hasSize(2);
        assertThat(filteredFlights).isEqualTo(flights);
    }

    @Test
    void testAirlineProcessorEarliestFlightPerAirline() {
        var processor = new AirlineFlightProcessor(null);

        var flights = List.of(
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 12,12,0), new BigDecimal(3000), "SU", "/example"),
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 13,12,0), new BigDecimal(4000), "SU", "/example-later"),
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 13,12,0), new BigDecimal(5000), "N4", "/example")
        );

        var query = FlightQuery.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .fromDate(LocalDate.of(2024, 12, 12))
                .toDate(LocalDate.of(2024, 12, 13))
                .currency("RUB")
                .price(BigDecimal.valueOf(4500))
                .build();

        var filteredFlights = processor.process(flights, query);

        assertThat(filteredFlights).hasSize(2);
        assertThat(filteredFlights)
                .anyMatch(f -> f.getAirline().equals("SU") && f.getDepartureAt().equals(LocalDateTime.of(2024, 12, 12,12,0)));
        assertThat(filteredFlights)
                .anyMatch(f -> f.getAirline().equals("N4") && f.getDepartureAt().equals(LocalDateTime.of(2024, 12, 13,12,0)));
    }

    @Test
    void testAirlineProcessorResolveConflicts() {
        FlightProcessor airlineProcessor = new AirlineFlightProcessor(null);

        var flights = List.of(
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 15,12,0), new BigDecimal(3000), "SU", "/example1"), // Рейс 1
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 16,12,0), new BigDecimal(4000), "SU", "/example2"), // Рейс 2 (позже)
                new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 14,12,0), new BigDecimal(5000), "SU", "/example3")  // Рейс 3 (раньше)
        );

        var query = FlightQuery.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .fromDate(LocalDate.of(2024, 12, 14))
                .toDate(LocalDate.of(2024, 12, 16))
                .currency("RUB")
                .build();

        var filteredFlights = airlineProcessor.process(flights, query);

        assertThat(filteredFlights).hasSize(1);

        var flight = filteredFlights.getFirst();
        assertThat(flight.getDepartureAt()).isEqualTo(LocalDateTime.of(2024, 12, 14,12,0));
        assertThat(flight.getAirline()).isEqualTo("SU");
        assertThat(flight.getPrice()).isEqualTo(new BigDecimal(5000));
    }

}