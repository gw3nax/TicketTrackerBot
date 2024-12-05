package ru.gw3nax.scrapper.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.gw3nax.scrapper.dto.request.Action;
import ru.gw3nax.scrapper.dto.request.FlightRequest;
import ru.gw3nax.scrapper.service.QueryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryConsumer {
    private final QueryService queryService;


    @RetryableTopic(
            attempts = "1",
            kafkaTemplate = "kafkaTemplate",
            dltTopicSuffix = "_dlq",
            include = RuntimeException.class
    )

    @KafkaListener(topics = "queries", groupId = "listen", containerFactory = "kafkaListener")
    public void listen(@Payload FlightRequest flightRequest, @Header("client-name") String header, @Header("action")String actionHeader, Acknowledgment acknowledgment) {
        log.info("Header: " + header);
        var action = Action.fromValue(actionHeader);
        switch (action) {
            case POST -> queryService.addSearchQuery(flightRequest, header);
            case DELETE -> queryService.deleteSearchQuery(flightRequest, header);
            case PUT -> queryService.updateSearchQuery(flightRequest, header);
        }
        acknowledgment.acknowledge();
    }
}
