package ru.gw3nax.scrapper.searcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gw3nax.scrapper.command.FlightCommand;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.dto.request.BotFlightRequest;
import ru.gw3nax.scrapper.entity.FlightQuery;
import ru.gw3nax.scrapper.processor.FlightProcessor;
import ru.gw3nax.scrapper.repository.FlightQueryRepository;
import ru.gw3nax.scrapper.service.BotService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightSearcher {
    private final FlightQueryRepository flightQueryRepository;
    private final BotService botService;
    private final List<FlightCommand> flightCommands;
    private final FlightProcessor flightProcessor;

    public void search() {
        List<FlightQuery> flightQueries = flightQueryRepository.findAll();
        for (var flightQuery : flightQueries) {
            List<BotFlightData> flightResponses = new ArrayList<>();
            for (var flightCommand : flightCommands) {
                flightResponses.addAll(Objects.requireNonNull(flightCommand.execute(flightQuery)));
            }
            List<BotFlightData> responses = flightProcessor.process(flightResponses, flightQuery);
            if (!responses.isEmpty()) {
                botService.sendUpdate(BotFlightRequest.builder()
                        .data(responses)
                        .userId(flightQuery.getUserId())
                        .build());
            }
        }
    }
}
