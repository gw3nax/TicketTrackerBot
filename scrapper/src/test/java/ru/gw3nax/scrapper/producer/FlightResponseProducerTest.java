package ru.gw3nax.scrapper.producer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.dto.request.BotFlightRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FlightResponseProducerTest {

    @Mock
    private KafkaTemplate<String, BotFlightRequest> kafkaTemplate;

    @InjectMocks
    private FlightResponseProducer flightResponseProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendUpdate() {
        var flightData = BotFlightData.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .departureAt(LocalDateTime.of(2024, 12, 15, 12, 0))
                .price(BigDecimal.valueOf(5000))
                .airline("Aeroflot")
                .link("http://example.com")
                .build();

        List<BotFlightData> flightDataList = new ArrayList<>();
        flightDataList.add(flightData);

        BotFlightRequest botFlightRequest = BotFlightRequest.builder()
                .userId("user123")
                .data(flightDataList)
                .build();
        String topicName = "flight-updates";

        flightResponseProducer.sendUpdate(botFlightRequest, topicName);

        verify(kafkaTemplate, times(1)).send(topicName, botFlightRequest);
    }
}
