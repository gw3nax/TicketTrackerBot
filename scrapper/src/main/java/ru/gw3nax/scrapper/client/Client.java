package ru.gw3nax.scrapper.client;

import org.springframework.web.reactive.function.client.WebClient;

public interface Client {
    WebClient getWebClient();
}
