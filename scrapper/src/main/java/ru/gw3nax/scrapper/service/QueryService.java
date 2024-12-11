package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gw3nax.scrapper.dto.request.FlightRequest;
import ru.gw3nax.scrapper.entity.FlightQuery;
import ru.gw3nax.scrapper.repository.FlightQueryRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueryService {
    private final FlightQueryRepository flightQueryRepository;
    private final ConversionService conversionService;
    private final UserService userService;

    @Transactional
    public void addSearchQuery(FlightRequest flightRequest, String header) {
        var flightQuery = conversionService.convert(flightRequest, FlightQuery.class);
        var user = userService.registerUser(flightRequest.getUserId(), header);
        flightQuery.setUser(user);
        flightQueryRepository.saveIfNotExist(flightQuery);
    }

    @Transactional
    public void deleteSearchQuery(FlightRequest flightRequest) {
        log.info("Delete flight query: {}", flightRequest);
        flightQueryRepository.deleteFlightQueryByUserId(userService.findUserByUserId(flightRequest.getUserId()));
    }

    @Transactional
    public void updateSearchQuery(FlightRequest flightRequest) {
        var flightQuery = flightQueryRepository.findFlightQueryByUserId(userService.findUserByUserId(flightRequest.getUserId()));
        if (flightQuery.isPresent()) {
            var updateFlightQuery = conversionService.convert(flightRequest, FlightQuery.class);
            updateFlightQuery.setId(flightQuery.get().getId());
            flightQueryRepository.save(updateFlightQuery);
        }
    }
}
