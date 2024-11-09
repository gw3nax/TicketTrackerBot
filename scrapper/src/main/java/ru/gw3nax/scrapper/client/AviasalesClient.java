package ru.gw3nax.scrapper.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gw3nax.scrapper.configuration.ApplicationConfig;

import java.util.Map;

@Configuration
public class AviasalesClient implements Client {

    private final String BASE_URL;
    private final String API_KEY;

    AviasalesClient(ApplicationConfig config) {
        BASE_URL = config.aviasalesBaseUrl();
        API_KEY = config.aviasalesApiKey();
    }

    @Override
    @Bean("aviasalesWebClient")
    public WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultUriVariables(Map.of("token", API_KEY))
                .build();
    }
}
