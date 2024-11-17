package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.gw3nax.scrapper.client.AviasalesClient;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AviasalesService {
    private final AviasalesClient aviasalesClient;
    private final ConversionService conversionService;

    public List<BotFlightData>getTickets(FlightQuery query) {
        return aviasalesClient.getTickets(query)
                .getData()
                .values()
                .stream()
                .map(data -> conversionService.convert(data, BotFlightData.class))
                .collect(Collectors.toList());
    }
}