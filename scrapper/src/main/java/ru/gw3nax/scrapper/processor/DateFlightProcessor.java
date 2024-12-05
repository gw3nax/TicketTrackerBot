package ru.gw3nax.scrapper.processor;

import lombok.extern.slf4j.Slf4j;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DateFlightProcessor extends FlightProcessor {

    public DateFlightProcessor(FlightProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public List<BotFlightData> filterData(List<BotFlightData> data, FlightQuery query) {
        if (query.getToDate() == null) {
            log.info("No date limit provided. Returning all data.");
            return data;
        }

        var fromDate = query.getFromDate();
        var toDate = query.getToDate();

        log.info("Filtering flights between {} and {}", fromDate, toDate);

        List<BotFlightData> filtered = data.stream()
                .filter(flight -> {
                    var flightDate = flight.getDepartureAt();
                    return !flightDate.isBefore(fromDate) && !flightDate.isAfter(toDate);
                })
                .collect(Collectors.toList());

        log.info("Filtered flights: {}", filtered.size());
        return filtered;
    }
}
