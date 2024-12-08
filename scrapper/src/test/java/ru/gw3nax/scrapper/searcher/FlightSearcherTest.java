package ru.gw3nax.scrapper.searcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.gw3nax.scrapper.command.FlightCommand;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.dto.request.BotFlightRequest;
import ru.gw3nax.scrapper.entity.FlightQuery;
import ru.gw3nax.scrapper.processor.FlightProcessor;
import ru.gw3nax.scrapper.repository.FlightQueryRepository;
import ru.gw3nax.scrapper.service.BotService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class FlightSearcherTest {

    @Mock
    private FlightQueryRepository flightQueryRepository;

    @Mock
    private BotService botService;

    @Mock
    private List<FlightCommand> flightCommands;

    @Mock
    private FlightProcessor flightProcessor;

    @InjectMocks
    private FlightSearcher flightSearcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchWithResults() {
        FlightQuery query = FlightQuery.builder()
                .userId("user123")
                .clientTopicName("topic123")
                .fromPlace("LED")
                .toPlace("SVO")
                .currency("USD")
                .price(BigDecimal.valueOf(5000))
                .build();

        BotFlightData flightData = new BotFlightData("LED", "SVO", LocalDateTime.of(2024, 12, 12, 12, 0), BigDecimal.valueOf(4500), "SU", "/example-link");

        when(flightQueryRepository.findAll()).thenReturn(List.of(query));
        FlightCommand command = mock(FlightCommand.class);
        when(command.execute(query)).thenReturn(List.of(flightData));
        when(flightCommands.iterator()).thenReturn(Collections.singletonList(command).iterator());

        when(flightProcessor.process(List.of(flightData), query)).thenReturn(List.of(flightData));

        flightSearcher.search();

        verify(flightQueryRepository, times(1)).findAll();
        verify(command, times(1)).execute(query);
        verify(flightProcessor, times(1)).process(List.of(flightData), query);
        verify(botService, times(1)).sendUpdate(
                BotFlightRequest.builder()
                        .data(List.of(flightData))
                        .userId("user123")
                        .build(),
                "topic123"
        );
    }

    @Test
    void testSearchWithoutResults() {
        when(flightQueryRepository.findAll()).thenReturn(Collections.emptyList());

        flightSearcher.search();

        verify(flightQueryRepository, times(1)).findAll();
        verifyNoInteractions(botService, flightProcessor);
    }
}
