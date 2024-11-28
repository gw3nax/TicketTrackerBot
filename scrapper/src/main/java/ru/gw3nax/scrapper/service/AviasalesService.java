package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.gw3nax.scrapper.client.AviasalesClient;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.dto.response.AviasalesTicketsResponse;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AviasalesService {
    private final AviasalesClient aviasalesClient;
    private final ConversionService conversionService;

    public List<BotFlightData> getTickets(FlightQuery query) {
        List<AviasalesTicketsResponse> responses = new ArrayList<>();
        String groupBy = "";

        if (query.getToDate() != null) {
            groupBy = "departure_at";
            LocalDate fromDate = query.getFromDate();
            LocalDate toDate = query.getToDate();
            YearMonth startMonth = YearMonth.from(fromDate);
            YearMonth endMonth = YearMonth.from(toDate);

            for (YearMonth currentMonth = startMonth; !currentMonth.isAfter(endMonth); currentMonth = currentMonth.plusMonths(1)) {
                FlightQuery tempQuery = new FlightQuery();
                tempQuery.setFromDate(currentMonth.atDay(1));
                tempQuery.setFromPlace(query.getFromPlace());
                tempQuery.setToPlace(query.getToPlace());
                tempQuery.setCurrency(query.getCurrency());

                AviasalesTicketsResponse response = aviasalesClient.getTickets(tempQuery, groupBy);
                responses.add(response);
            }
        } else {
            AviasalesTicketsResponse response = aviasalesClient.getTickets(query, groupBy);
            responses.add(response);
        }

        return responses.stream()
                .flatMap(response -> response.getData().values().stream())
                .map(data -> conversionService.convert(data, BotFlightData.class))
                .collect(Collectors.toList());
    }
}
