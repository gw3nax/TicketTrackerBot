package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import ru.gw3nax.scrapper.dto.request.BotFlightResponse;
import ru.gw3nax.scrapper.dto.response.AviasalesTicketsResponse;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AviasalesService {
    private final WebClient aviasalesWebClient;
    private final ConversionService conversionService;

    public Mono<List<BotFlightResponse>> getTickets(FlightQuery query) {
        return aviasalesWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("departure_at", query.getFromDate())
                        .queryParam("origin", query.getFromPlace())
                        .queryParam("destination", query.getToPlace())
                        .queryParam("currency", query.getCurrency())
                        .queryParam("token", "edcc47e0fe063c3c32423a34ed9dcb85")
                        .build())
                .retrieve()
                .bodyToMono(AviasalesTicketsResponse.class)
                .map(aviasalesTicketsResponse ->
                        aviasalesTicketsResponse.getData().values().stream()
                                .map(data -> conversionService.convert(data, BotFlightResponse.class))
                                .collect(Collectors.toList())
                );
    }
}