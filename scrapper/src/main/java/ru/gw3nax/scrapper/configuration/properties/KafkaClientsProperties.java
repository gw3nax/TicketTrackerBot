package ru.gw3nax.scrapper.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "kafka-clients")
public record KafkaClientsProperties(
        Map<String, KafkaClientConfig> clientsProps
) {

    public record KafkaClientConfig(
            String topicName
    ) {
    }
}
