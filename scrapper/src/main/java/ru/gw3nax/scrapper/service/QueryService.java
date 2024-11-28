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
    private final TopicService topicService;

    @Transactional
    public void addSearchQuery(FlightRequest flightRequest, String header) {
        if (!flightQueryRepository.existsFlightQueryByClientTopicName(header)) {
            topicService.createNewTopic(header);
        }
        var flightQuery = conversionService.convert(flightRequest, FlightQuery.class);
        flightQuery.setClientTopicName(header);
        log.info("Flight Query: " + flightQuery.getClientTopicName());
        flightQueryRepository.saveIfNotExist(flightQuery);
    }

    @Transactional
    public void deleteSearchQuery(FlightRequest flightRequest, String header) {
        flightQueryRepository.deleteFlightQueryByFlightRequestAndHeader(flightRequest, header);
    }

    @Transactional
    public void updateSearchQuery(FlightRequest flightRequest, String header) {
        var flightQuery = flightQueryRepository.findFlightQueryByClientTopicNameAndAndUserId(header, flightRequest.getUserId());
        if (flightQuery.isPresent()) {
            var updateFlightQuery = conversionService.convert(flightRequest, FlightQuery.class);
            updateFlightQuery.setClientTopicName(header);
            updateFlightQuery.setId(flightQuery.get().getId());
            flightQueryRepository.save(updateFlightQuery);
        }
    }
}
