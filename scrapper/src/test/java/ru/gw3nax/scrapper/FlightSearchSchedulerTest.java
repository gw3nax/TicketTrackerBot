package ru.gw3nax.scrapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.gw3nax.scrapper.searcher.FlightSearcher;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FlightSearchSchedulerTest {

    @Mock
    private FlightSearcher flightSearcher;

    @InjectMocks
    private FlightSearchScheduler flightSearchScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldInvokeSearchAndLogInfo() {
        flightSearchScheduler.schedule();
        verify(flightSearcher, times(1)).search();

    }
}
