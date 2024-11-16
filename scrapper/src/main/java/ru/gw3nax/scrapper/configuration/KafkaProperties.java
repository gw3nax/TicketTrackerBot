package ru.gw3nax.scrapper.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "kafka")
public record KafkaProperties(
        @NotNull
        Integer batchSize,
        @NotNull
        Integer maxRequestSize,
        @NotNull
        Long lingerMs,
        @NotEmpty
        String acks,
        @Bean
        TopicProp topicProp,
        @NotNull
        String bootstrapServer
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
}


