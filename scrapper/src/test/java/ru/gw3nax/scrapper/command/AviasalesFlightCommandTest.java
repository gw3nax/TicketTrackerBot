package ru.gw3nax.scrapper.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.entity.FlightQuery;
import ru.gw3nax.scrapper.service.AviasalesService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AviasalesFlightCommandTest {

    @Mock
    private AviasalesService aviasalesService;

    @InjectMocks
    private AviasalesFlightCommand aviasalesFlightCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnFlightDataList() {
        FlightQuery query = FlightQuery.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .fromDate(LocalDate.of(2024, 12, 1))
                .currency("RUB")
                .build();

        BotFlightData botFlightData = BotFlightData.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .price(new BigDecimal(250.00))
                .departureAt(LocalDate.of(2024, 12, 1))
                .build();

        when(aviasalesService.getTickets(query)).thenReturn(List.of(botFlightData));

        List<BotFlightData> result = aviasalesFlightCommand.execute(query);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("LED", result.get(0).getFromPlace());
        assertEquals("SVO", result.get(0).getToPlace());
        assertEquals(new BigDecimal(250.00), result.get(0).getPrice());
        assertEquals(LocalDate.of(2024, 12, 1), result.get(0).getDepartureAt());

        verify(aviasalesService, times(1)).getTickets(query);
    }

    @Test
    void shouldHandleEmptyResponse() {
        FlightQuery query = FlightQuery.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .fromDate(LocalDate.of(2024, 12, 1))
                .currency("RUB")
                .build();

        when(aviasalesService.getTickets(query)).thenReturn(List.of());
        List<BotFlightData> result = aviasalesFlightCommand.execute(query);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(aviasalesService, times(1)).getTickets(query);
    }
}
