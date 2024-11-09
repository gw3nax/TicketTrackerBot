package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.gw3nax.scrapper.dto.request.BotFlightRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class BotService {

    private final WebClient botWebClient;

    public Mono<Void> sendTicketSearchResult(BotFlightRequest flightBotRequest) {
        log.info("Sending search result: " + flightBotRequest);
        return botWebClient.post()
                .uri("")
                .body(Mono.just(flightBotRequest), BotFlightRequest.class)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
