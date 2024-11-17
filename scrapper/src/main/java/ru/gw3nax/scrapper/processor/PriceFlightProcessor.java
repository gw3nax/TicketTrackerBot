package ru.gw3nax.scrapper.processor;

import lombok.extern.slf4j.Slf4j;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class PriceFlightProcessor extends FlightProcessor {

    public PriceFlightProcessor(FlightProcessor nextProcessor) {
        super(nextProcessor);
    }


    @Override
    public List<BotFlightData> filterData(List<BotFlightData> data, FlightQuery query) {
        if (query.getPrice() == null) {
            log.info("No price limit provided. Returning all data.");
            return data;
        }

        BigDecimal priceLimit = query.getPrice();
        List<BotFlightData> filtered = data.stream()
                .filter(flight -> {
                    BigDecimal flightPrice = flight.getPrice();
                    boolean isWithinLimit = flightPrice != null && flightPrice.compareTo(priceLimit) <= 0;
                    log.info("Flight: {} with price: {} is within limit: {}", flight, flightPrice, isWithinLimit);
                    return isWithinLimit;
                })
                .toList();

        log.info("Filtered flights: {}", filtered);
        return filtered;
    }
}
