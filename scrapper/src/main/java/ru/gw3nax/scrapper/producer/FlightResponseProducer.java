package ru.gw3nax.scrapper.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.gw3nax.scrapper.dto.request.BotFlightRequest;

@Component
@RequiredArgsConstructor
public class FlightResponseProducer {
    private final KafkaTemplate<String, BotFlightRequest> kafkaTemplate;

    public void sendUpdate(BotFlightRequest botFlightRequest, String topicName) {
        kafkaTemplate.send(topicName, botFlightRequest);
    }
}
