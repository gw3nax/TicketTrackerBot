package ru.gw3nax.scrapper.command;

import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.util.List;

public interface FlightCommand {
    List<BotFlightData> execute(FlightQuery query);
}
