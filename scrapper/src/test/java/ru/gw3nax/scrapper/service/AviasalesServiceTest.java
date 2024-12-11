package ru.gw3nax.scrapper.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import ru.gw3nax.scrapper.client.AviasalesClient;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.dto.response.AviasalesResponseData;
import ru.gw3nax.scrapper.dto.response.AviasalesTicketsResponse;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AviasalesServiceTest {

    @Mock
    private AviasalesClient aviasalesClient;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private AviasalesService aviasalesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTicketsWithDateRange() {
        AviasalesTicketsResponse mockResponse = new AviasalesTicketsResponse();
        mockResponse.setData(Map.of(
                "2024-12-01", new AviasalesResponseData("LED", "SVO", "/link1", new BigDecimal("1825.50"), 0, "2024-12-01T10:00:00", "SU"),
                "2024-12-02", new AviasalesResponseData("LED", "SVO", "/link2", new BigDecimal("1925.50"), 1, "2024-12-02T11:00:00", "DP")
        ));
        when(aviasalesClient.getTickets(any(FlightQuery.class), eq("departure_at"))).thenReturn(mockResponse);

        BotFlightData convertedData1 = new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 1,12,0), new BigDecimal("1825.50"), "SU", "/link1");
        BotFlightData convertedData2 = new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 2,12,0), new BigDecimal("1925.50"), "DP", "/link2");
        when(conversionService.convert(any(AviasalesResponseData.class), eq(BotFlightData.class)))
                .thenReturn(convertedData1, convertedData2);

        FlightQuery query = FlightQuery.builder()
                .fromDate(LocalDate.of(2024, 12, 1))
                .toDate(LocalDate.of(2024, 12, 31))
                .fromPlace("LED")
                .toPlace("SVO")
                .currency("USD")
                .build();

        List<BotFlightData> result = aviasalesService.getTickets(query);

        verify(aviasalesClient, times(1)).getTickets(any(FlightQuery.class), eq("departure_at"));
        verify(conversionService, times(2)).convert(any(AviasalesResponseData.class), eq(BotFlightData.class));

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(convertedData1, convertedData2);
    }

    @Test
    void testGetTicketsWithoutDateRange() {
        AviasalesTicketsResponse mockResponse = new AviasalesTicketsResponse();
        mockResponse.setData(Map.of(
                "2024-12-01", new AviasalesResponseData("LED", "SVO", "/link1", new BigDecimal("1825.50"), 0, "2024-12-01T10:00:00", "SU")
        ));
        when(aviasalesClient.getTickets(any(FlightQuery.class), eq(""))).thenReturn(mockResponse);
        BotFlightData convertedData = new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 1,12,0), new BigDecimal("1825.50"), "SU", "/link1");
        when(conversionService.convert(any(AviasalesResponseData.class), eq(BotFlightData.class))).thenReturn(convertedData);
        FlightQuery query = FlightQuery.builder()
                .fromDate(LocalDate.of(2024, 12, 1))
                .fromPlace("LED")
                .toPlace("SVO")
                .currency("USD")
                .build();

        List<BotFlightData> result = aviasalesService.getTickets(query);
        verify(aviasalesClient, times(1)).getTickets(query, "");
        verify(conversionService, times(1)).convert(any(AviasalesResponseData.class), eq(BotFlightData.class));
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(convertedData);
    }
}
