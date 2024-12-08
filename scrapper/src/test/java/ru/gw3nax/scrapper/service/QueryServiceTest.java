package ru.gw3nax.scrapper.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.gw3nax.scrapper.entity.FlightQuery;
import ru.gw3nax.scrapper.repository.FlightQueryRepository;
import ru.gw3nax.scrapper.dto.request.FlightRequest;
import org.springframework.core.convert.ConversionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

class QueryServiceTest {

    @Mock
    private FlightQueryRepository flightQueryRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private QueryService queryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSearchQuery() {
        var flightRequest = FlightRequest.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .fromDate(LocalDate.of(2024,12,12))
                .toDate(LocalDate.of(2024,12,15))
                .currency("RUB")
                .price(BigDecimal.valueOf(5000))
                .build();
        FlightQuery flightQuery = new FlightQuery();
        flightQuery.setClientTopicName("test-header");

        when(conversionService.convert(flightRequest, FlightQuery.class)).thenReturn(flightQuery);

        queryService.addSearchQuery(flightRequest, "test-header");

        verify(conversionService).convert(flightRequest, FlightQuery.class);
        verify(flightQueryRepository).saveIfNotExist(flightQuery);
    }

    @Test
    void testDeleteSearchQuery() {
        var flightRequest = FlightRequest.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .fromDate(LocalDate.of(2024,12,12))
                .toDate(LocalDate.of(2024,12,15))
                .currency("RUB")
                .price(BigDecimal.valueOf(5000))
                .build();
        queryService.deleteSearchQuery(flightRequest, "test-header");
        verify(flightQueryRepository).deleteFlightQueryByFlightRequestAndHeader(flightRequest, "test-header");
    }

    @Test
    void testUpdateSearchQuery() {
        var flightRequest = FlightRequest.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .fromDate(LocalDate.of(2024,12,12))
                .toDate(LocalDate.of(2024,12,15))
                .currency("RUB")
                .price(BigDecimal.valueOf(5000))
                .userId("user123")
                .build();
        FlightQuery existingQuery = new FlightQuery();
        existingQuery.setId(1L);
        existingQuery.setClientTopicName("test-header");

        FlightQuery updatedQuery = new FlightQuery();
        updatedQuery.setClientTopicName("test-header");

        when(flightQueryRepository.findFlightQueryByClientTopicNameAndAndUserId("test-header", "user123"))
                .thenReturn(Optional.of(existingQuery));
        when(conversionService.convert(flightRequest, FlightQuery.class)).thenReturn(updatedQuery);

        queryService.updateSearchQuery(flightRequest, "test-header");

        verify(flightQueryRepository).findFlightQueryByClientTopicNameAndAndUserId("test-header", "user123");
        verify(conversionService).convert(flightRequest, FlightQuery.class);
        verify(flightQueryRepository).save(updatedQuery);

        assert updatedQuery.getId().equals(1L);
    }

}
