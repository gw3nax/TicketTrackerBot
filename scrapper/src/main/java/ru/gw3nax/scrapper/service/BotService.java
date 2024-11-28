package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.gw3nax.scrapper.dto.request.BotFlightRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class BotService {
    private final KafkaTemplate<String, BotFlightRequest> kafkaTemplate;

    public void sendUpdate(BotFlightRequest botFlightRequest, String topicName) {
        kafkaTemplate.send(topicName, botFlightRequest);
    }
}
