package ru.gw3nax.scrapper.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app")
public record ApplicationConfig(
        @NotEmpty String aviasalesBaseUrl,
        @NotEmpty String botBaseUrl,
        @NotEmpty String aviasalesApiKey,
        @NotNull
        @Bean
        Scheduler scheduler
) {
    public record Scheduler(boolean enable, @NotNull Duration interval) {
    }
}
