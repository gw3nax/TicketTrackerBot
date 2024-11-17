package ru.gw3nax.scrapper.processor;

import lombok.RequiredArgsConstructor;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.util.List;

@RequiredArgsConstructor
public abstract class FlightProcessor {
    private final FlightProcessor nextProcessor;

    public abstract List<BotFlightData> filterData(List<BotFlightData> data, FlightQuery query);

    public List<BotFlightData> process(List<BotFlightData> flightResponses, FlightQuery query) {
        var responses = filterData(flightResponses, query);
        if (nextProcessor != null) return nextProcessor.process(responses, query);
        return responses;
    }
}
