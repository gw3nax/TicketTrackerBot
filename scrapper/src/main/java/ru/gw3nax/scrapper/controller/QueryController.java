package ru.gw3nax.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gw3nax.scrapper.dto.request.FlightRequest;
import ru.gw3nax.scrapper.service.AviasalesService;
import ru.gw3nax.scrapper.service.QueryService;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class QueryController {
     private final QueryService queryService;

    @PostMapping("/search_query")
    public void addSearchQuery(@RequestBody FlightRequest flightRequest) {
        queryService.addSearchQuery(flightRequest);
    }
}
