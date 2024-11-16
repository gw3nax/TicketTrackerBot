package ru.gw3nax.scrapper.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import ru.gw3nax.scrapper.dto.response.AviasalesTicketsResponse;
import ru.gw3nax.scrapper.entity.FlightQuery;

@Component
@Slf4j
public class AviasalesClient {

    private final String BASE_URL;
    private final String API_KEY;
    private final RestClient customClient;

    public AviasalesClient(
            @Value("${app.aviasalesBaseUrl}") String baseUrl,
            @Value("${app.aviasalesApiKey}") String apiKey) {
        this.BASE_URL = baseUrl;
        this.API_KEY = apiKey;

        this.customClient = RestClient.builder()
                .baseUrl(UriComponentsBuilder.fromHttpUrl(BASE_URL)
                        .queryParam("token", API_KEY)
                        .toUriString())
                .build();
    }


    public AviasalesTicketsResponse getTickets(FlightQuery query) {
        var result = customClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("departure_at", query.getFromDate())
                        .queryParam("origin", query.getFromPlace())
                        .queryParam("destination", query.getToPlace())
                        .queryParam("currency", query.getCurrency())
                        .build())
                .retrieve()
                .body(AviasalesTicketsResponse.class);
        log.info(result.toString());
        return result;
    }
}
