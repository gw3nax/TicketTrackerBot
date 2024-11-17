package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gw3nax.scrapper.dto.request.FlightRequest;
import ru.gw3nax.scrapper.entity.FlightQuery;
import ru.gw3nax.scrapper.repository.FlightQueryRepository;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final FlightQueryRepository flightQueryRepository;
    private final ConversionService conversionService;

    @Transactional
    public void addSearchQuery(FlightRequest flightRequest) {
        if (flightRequest != null) {
            var flightQuery = conversionService.convert(flightRequest, FlightQuery.class);
            flightQueryRepository.save(flightQuery);

        } else throw new IllegalArgumentException("Flight request is null");
    }
}
