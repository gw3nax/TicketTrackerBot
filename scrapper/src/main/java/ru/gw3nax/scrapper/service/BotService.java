package ru.gw3nax.scrapper.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.gw3nax.scrapper.configuration.KafkaProducerProperties;
import ru.gw3nax.scrapper.dto.request.BotFlightRequest;

@Service
@Slf4j
public class BotService {
    private final KafkaProducerProperties kafkaProperties;
    private final KafkaTemplate<String, BotFlightRequest> kafkaTemplate;

    public BotService(
            KafkaProducerProperties kafkaProperties,
            KafkaTemplate<String, BotFlightRequest> kafkaTemplate
    ) {
        this.kafkaProperties = kafkaProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUpdate(BotFlightRequest botFlightRequest) {
        kafkaTemplate.send(kafkaProperties.topicProp().name(), botFlightRequest);
    }
}
