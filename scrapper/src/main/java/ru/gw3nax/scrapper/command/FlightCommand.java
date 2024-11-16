package ru.gw3nax.scrapper.command;

import reactor.core.publisher.Mono;
import ru.gw3nax.scrapper.dto.request.BotFlightResponse;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.util.List;

public interface FlightCommand {
    List<BotFlightResponse> execute(FlightQuery query);
}
