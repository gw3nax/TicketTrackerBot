package ru.gw3nax.scrapper.processor;

import lombok.extern.slf4j.Slf4j;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class AirlineFlightProcessor extends FlightProcessor {
    public AirlineFlightProcessor(FlightProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public List<BotFlightData> filterData(List<BotFlightData> data, FlightQuery query) {
        Map<String, BotFlightData> earliestFlightsByAirline = data.stream()
                .collect(Collectors.toMap(
                        BotFlightData::getAirline,
                        flight -> flight,
                        (existing, replacement) ->
                                existing.getDepartureAt().isBefore(replacement.getDepartureAt())
                                        ? existing
                                        : replacement
                ));

        List<BotFlightData> result = new ArrayList<>(earliestFlightsByAirline.values());

        log.info("Filtered flights (earliest per airline): " + result.size());
        return result;
    }
}
