package ru.gw3nax.scrapper.searcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gw3nax.scrapper.command.FlightCommand;
import ru.gw3nax.scrapper.dto.request.BotFlightResponse;
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
        //Проходится по всем запросам
        for (var flightQuery : flightQueries) {
            List<BotFlightResponse> flightResponses = new ArrayList<>();
            //Каждый запрос кидает
            for (var flightCommand : flightCommands) {
                flightResponses.addAll(Objects.requireNonNull(flightCommand.execute(flightQuery).block()));
            }
            List<BotFlightResponse> responses = flightProcessor.process(flightResponses);

            botService.sendTicketSearchResult(BotFlightRequest.builder()
                    .price(BigDecimal.ONE)
                    .currency("RUB")
                    .data(responses)
                    .chatId(flightQuery.getUser().getChatId())
                    .build());
        }
    }
}
