package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.gw3nax.scrapper.dto.request.FlightRequest;
import ru.gw3nax.scrapper.entity.FlightQuery;
import ru.gw3nax.scrapper.exception.exceptions.UserNotRegisteredException;
import ru.gw3nax.scrapper.repository.FlightQueryRepository;
import ru.gw3nax.scrapper.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final FlightQueryRepository flightQueryRepository;
    private final ConversionService conversionService;
    private final UserRepository userRepository;

    public void addSearchQuery(FlightRequest flightRequest) {
        if (flightRequest != null) {
            var flightQuery = conversionService.convert(flightRequest, FlightQuery.class);
            var user = userRepository.findByChatId(flightRequest.getChatId());

            if (user.isEmpty()) throw new UserNotRegisteredException("User does not exist");

            flightQuery.setUser(user.get());
            flightQueryRepository.save(flightQuery);

        } else throw new IllegalArgumentException("Flight request is null");
    }
}
