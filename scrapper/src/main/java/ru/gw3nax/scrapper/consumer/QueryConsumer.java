package ru.gw3nax.scrapper.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
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
    public void listen(@Payload FlightRequest flightRequest, @Header("client-name") String header, Acknowledgment acknowledgment) {
        log.info("Header: " + header);
        switch (flightRequest.getAction()) {
            case POST -> queryService.addSearchQuery(flightRequest, header);
            case DELETE -> queryService.deleteSearchQuery(flightRequest, header);
            case PUT -> queryService.updateSearchQuery(flightRequest, header);
        }
        acknowledgment.acknowledge();
    }
}
