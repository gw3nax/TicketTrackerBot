package ru.gw3nax.scrapper.configuration.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "kafka-producer")
public record KafkaProducerProperties(
        @NotNull
        Integer batchSize,
        @NotNull
        Integer maxRequestSize,
        @NotNull
        Long lingerMs,
        @NotEmpty
        String acks,
        TopicProp topicProp,
        @NotNull
        String bootstrapServer,
        Credential credential
) {

    public record TopicProp(
            @NotEmpty
            String name,
            @NotNull
            Integer partitions,
            @NotNull
            Integer replicas
    ) {
    }
    public record Credential(
            @NotEmpty String username,
            @NotEmpty String password
    ) {
    }
}


