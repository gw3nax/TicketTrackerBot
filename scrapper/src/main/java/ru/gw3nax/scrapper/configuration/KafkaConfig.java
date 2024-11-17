package ru.gw3nax.scrapper.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.gw3nax.scrapper.dto.request.BotFlightRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder
                .name(kafkaProperties.topicProp().name())
                .partitions(kafkaProperties.topicProp().partitions())
                .replicas(kafkaProperties.topicProp().replicas())
                .build();
    }

    @Bean
    public DefaultKafkaProducerFactory<String, BotFlightRequest> kafkaProducerFactory() {
        Map<String, Object> prop = new HashMap<>();

        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrapServer());

        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        prop.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProperties.batchSize());
        prop.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, kafkaProperties.maxRequestSize());
        prop.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProperties.lingerMs());

        prop.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.acks());

        prop.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class);

        return new DefaultKafkaProducerFactory<>(prop);
    }

    @Bean
    public KafkaTemplate<String, BotFlightRequest> kafkaTemplate(DefaultKafkaProducerFactory<String, BotFlightRequest> kafkaProducerFactory) {
        return new KafkaTemplate<>(kafkaProducerFactory);
    }

}
