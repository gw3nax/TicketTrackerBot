package ru.gw3nax.scrapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.gw3nax.scrapper.searcher.FlightSearcher;

@Configuration
@EnableScheduling
@ConditionalOnProperty(
        value = "app.scheduler.enable",
        havingValue = "true"
)
@Slf4j
@RequiredArgsConstructor
public class FlightSearchScheduler {
    private final FlightSearcher flightSearcher;

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void schedule(){
        log.info("Looking for flights...");
        flightSearcher.search();
    }
}
