package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gw3nax.scrapper.configuration.properties.KafkaClientsProperties;
import ru.gw3nax.scrapper.dto.request.BotFlightRequest;
import ru.gw3nax.scrapper.producer.FlightResponseProducer;

@Service
@RequiredArgsConstructor
@Slf4j
public class BotService {
    private final FlightResponseProducer flightResponseProducer;
    private final KafkaClientsProperties kafkaClientsProperties;

    public void sendUpdate(BotFlightRequest botFlightRequest, String clientName) {
        var topicName = kafkaClientsProperties.clientsProps().get(clientName).topicName();
        flightResponseProducer.sendUpdate(botFlightRequest, topicName);
    }
}
