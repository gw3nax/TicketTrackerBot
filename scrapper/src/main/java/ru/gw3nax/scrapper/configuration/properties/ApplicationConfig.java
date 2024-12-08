package ru.gw3nax.scrapper.configuration.properties;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app")
public record ApplicationConfig(
        @NotNull
        @Bean
        Scheduler scheduler
) {
    public record Scheduler(boolean enable, @NotNull Duration interval) {
    }
}
