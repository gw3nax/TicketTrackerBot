package ru.gw3nax.scrapper.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.gw3nax.scrapper.dto.request.FlightRequest;
import ru.gw3nax.scrapper.service.QueryService;

@Service
@RequiredArgsConstructor
public class QueryConsumer {
    private final QueryService queryService;


    @RetryableTopic(
            attempts = "1",
            kafkaTemplate = "kafkaTemplate",
            dltTopicSuffix = "_dlq",
            include = RuntimeException.class
    )

    @KafkaListener(topics = "queries", groupId = "listen", containerFactory = "kafkaListener")
    public void listen(@Payload FlightRequest flightRequest, Acknowledgment acknowledgment) {
        queryService.addSearchQuery(flightRequest);
        acknowledgment.acknowledge();
    }
}
