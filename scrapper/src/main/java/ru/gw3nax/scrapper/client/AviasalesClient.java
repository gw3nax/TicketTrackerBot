package ru.gw3nax.scrapper.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import ru.gw3nax.scrapper.dto.response.AviasalesTicketsResponse;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class AviasalesClient {
    private final RestClient customClient;

    public AviasalesClient(
            @Value("${app.aviasalesBaseUrl}") String baseUrl,
            @Value("${app.aviasalesApiKey}") String token) {

        this.customClient = RestClient.builder()
                .baseUrl(UriComponentsBuilder.fromHttpUrl(baseUrl)
                        .queryParam("token", token)
                        .toUriString())
                .build();
    }

    public AviasalesTicketsResponse getTickets(FlightQuery query, String groupBy) {
        log.info(groupBy.isEmpty() ? "GroupBy is empty" : "GroupBy = " + groupBy);
        log.info("FlightQuery = " + query.getFromDate());


        String formattedDate = query.getFromDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        log.info("Formatted Date = " + formattedDate);

        var result = customClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("departure_at", formattedDate)
                        .queryParam("origin", query.getFromPlace())
                        .queryParam("destination", query.getToPlace())
                        .queryParam("currency", query.getCurrency())
                        .queryParam("group_by", groupBy)
                        .build())
                .retrieve()
                .body(AviasalesTicketsResponse.class);
        log.info(result.toString());
        return result;
    }
}
