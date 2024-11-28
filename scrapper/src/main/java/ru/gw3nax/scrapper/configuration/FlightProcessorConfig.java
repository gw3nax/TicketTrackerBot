package ru.gw3nax.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gw3nax.scrapper.processor.DateFlightProcessor;
import ru.gw3nax.scrapper.processor.FlightProcessor;
import ru.gw3nax.scrapper.processor.PriceFlightProcessor;

@Configuration
public class FlightProcessorConfig {

    @Bean
    public FlightProcessor flightProcessor() {
        var priceFlightProcessor = new PriceFlightProcessor(null);
        var dateFlightProcessor = new DateFlightProcessor(priceFlightProcessor);
        return dateFlightProcessor;
    }
}
