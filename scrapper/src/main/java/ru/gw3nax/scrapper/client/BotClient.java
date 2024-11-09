package ru.gw3nax.scrapper.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gw3nax.scrapper.configuration.ApplicationConfig;

@Configuration
public class BotClient implements Client {

    private final String BASE_URL;

    BotClient(ApplicationConfig config) {
        BASE_URL = config.botBaseUrl();
    }

    @Override
    @Bean("botWebClient")
    public WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }
}
