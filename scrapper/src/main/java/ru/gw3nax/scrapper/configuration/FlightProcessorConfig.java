package ru.gw3nax.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gw3nax.scrapper.processor.FlightProcessor;
import ru.gw3nax.scrapper.processor.PriceFlightProcessor;

@Configuration
public class FlightProcessorConfig {

    @Bean
    public FlightProcessor flightProcessor() {
        PriceFlightProcessor priceFlightProcessor = new PriceFlightProcessor(null);
        return priceFlightProcessor;
    }
}
