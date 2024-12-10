package ru.gw3nax.scrapper.producer;

import io.github.springwolf.bindings.kafka.annotations.KafkaAsyncOperationBinding;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import io.github.springwolf.core.asyncapi.annotations.AsyncPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.gw3nax.scrapper.dto.request.BotFlightRequest;

@Component
@RequiredArgsConstructor
public class FlightResponseProducer {
    private final KafkaTemplate<String, BotFlightRequest> kafkaTemplate;


    @AsyncPublisher(
            operation = @AsyncOperation(
                    channelName = "telegram-bot-1-responses",
                    description = "More details for the outgoing topic"
            )
    )
    @KafkaAsyncOperationBinding
    public void sendUpdate(@Payload  BotFlightRequest botFlightRequest, String topicName) {
        kafkaTemplate.send(topicName, botFlightRequest);
    }
}
