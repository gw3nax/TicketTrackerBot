package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import ru.gw3nax.scrapper.client.AviasalesClient;
import ru.gw3nax.scrapper.dto.request.BotFlightResponse;
import ru.gw3nax.scrapper.dto.response.AviasalesTicketsResponse;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AviasalesService {
    private final AviasalesClient aviasalesClient;
    private final ConversionService conversionService;

    public List<BotFlightResponse>getTickets(FlightQuery query) {
        return aviasalesClient.getTickets(query)
                .getData()
                .values()
                .stream()
                .map(data -> conversionService.convert(data, BotFlightResponse.class))
                .collect(Collectors.toList());
    }
}