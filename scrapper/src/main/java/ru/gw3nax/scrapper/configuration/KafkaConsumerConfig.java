package ru.gw3nax.scrapper.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.gw3nax.scrapper.dto.request.BotFlightRequest;
import ru.gw3nax.scrapper.dto.request.FlightRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaConsumerProperties kafkaProperties;

    @Bean
    public DefaultKafkaConsumerFactory<String, FlightRequest> kafkaConsumerFactory() {
        JsonDeserializer<FlightRequest> deserializer = new JsonDeserializer<>(FlightRequest.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> consumerProps = new HashMap<>();

        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrapServer());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.groupId());
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.enableAutoCommit());
        consumerProps.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, kafkaProperties.isolationLevel());
        consumerProps.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());

        return new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FlightRequest> kafkaListener(
            DefaultKafkaConsumerFactory<String, FlightRequest> kafkaConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, FlightRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(kafkaConsumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return factory;
    }


}
