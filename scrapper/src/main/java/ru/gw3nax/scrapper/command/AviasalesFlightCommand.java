package ru.gw3nax.scrapper.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.entity.FlightQuery;
import ru.gw3nax.scrapper.service.AviasalesService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AviasalesFlightCommand implements FlightCommand {

    private final AviasalesService aviasalesService;

    @Override
    public List<BotFlightData> execute(FlightQuery query) {
        return aviasalesService.getTickets(query);
    }
}
