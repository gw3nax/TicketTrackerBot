package ru.gw3nax.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.gw3nax.scrapper.configuration.ApplicationConfig;
import ru.gw3nax.scrapper.configuration.KafkaConsumerProperties;
import ru.gw3nax.scrapper.configuration.KafkaProducerProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, KafkaProducerProperties.class, KafkaConsumerProperties.class})
public class ScrapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
    }

}
