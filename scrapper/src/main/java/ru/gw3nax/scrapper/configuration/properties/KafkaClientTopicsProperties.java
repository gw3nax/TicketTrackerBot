package ru.gw3nax.scrapper.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Setter
@Getter
@ConfigurationProperties(prefix = "kafka-clients")
public class KafkaClientTopicsProperties {
    private Map<String, String> clientsProps;
}
