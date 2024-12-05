package ru.gw3nax.scrapper.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.entity.FlightQuery;
import ru.gw3nax.scrapper.service.AviasalesService;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class AviasalesFlightCommand implements FlightCommand {

    private final AviasalesService aviasalesService;

    @Override
    public List<BotFlightData> execute(FlightQuery query) {
        var response = aviasalesService.getTickets(query);
        return response;
    }
}
