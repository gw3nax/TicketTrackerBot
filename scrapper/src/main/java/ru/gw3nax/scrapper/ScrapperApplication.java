package ru.gw3nax.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.gw3nax.scrapper.configuration.properties.ApplicationConfig;
import ru.gw3nax.scrapper.configuration.properties.KafkaClientTopicsProperties;
import ru.gw3nax.scrapper.configuration.properties.KafkaConsumerProperties;
import ru.gw3nax.scrapper.configuration.properties.KafkaProducerProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class, KafkaProducerProperties.class, KafkaConsumerProperties.class, KafkaClientTopicsProperties.class})
public class ScrapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
    }
}
