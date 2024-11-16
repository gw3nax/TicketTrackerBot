package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.gw3nax.scrapper.configuration.KafkaProperties;
import ru.gw3nax.scrapper.dto.request.BotFlightResponse;

@Service
@Slf4j
public class BotService {
    private final KafkaProperties kafkaProperties;
    private final KafkaTemplate<String, BotFlightResponse> kafkaTemplate;

    public BotService(
            KafkaProperties kafkaProperties,
            KafkaTemplate<String, BotFlightResponse> kafkaTemplate
    ) {
        this.kafkaProperties = kafkaProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<Void> sendUpdate(BotFlightResponse botFlightResponse) {
        kafkaTemplate.send(kafkaProperties.topicProp().name(), botFlightResponse);
        return Mono.empty();
    }
}
