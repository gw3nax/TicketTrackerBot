package ru.gw3nax.scrapper.processor;

import lombok.RequiredArgsConstructor;
import ru.gw3nax.scrapper.dto.request.BotFlightResponse;

import java.util.List;

@RequiredArgsConstructor
public abstract class FlightProcessor {
    private final FlightProcessor nextProcessor;


    public List<BotFlightResponse> process(List<BotFlightResponse> flightResponses) {
        if (nextProcessor != null) return nextProcessor.process(flightResponses);
        return flightResponses;
    }
}
