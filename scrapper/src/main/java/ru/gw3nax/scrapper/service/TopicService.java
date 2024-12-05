package ru.gw3nax.scrapper.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;
import ru.gw3nax.scrapper.configuration.properties.KafkaClientsProperties;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class TopicService {

    private final KafkaClientsProperties kafkaClientsProperties;
    private final KafkaAdmin kafkaAdmin;

    @PostConstruct
    public void init() {
        log.info("Kafka Clients props is {}", kafkaClientsProperties.clientsProps());
        if (kafkaClientsProperties.clientsProps()!= null) {
            for (var topic : kafkaClientsProperties.clientsProps().keySet()) {
                createNewTopic(topic);
            }
        } else {
            log.error("Kafka clients properties are not initialized.");
        }
    }

    private void createNewTopic(String topicName) {
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            NewTopic topic = new NewTopic(topicName, 1, (short) 1);
            adminClient.createTopics(Collections.singletonList(topic));
            log.info("Топик {} успешно создан", topicName);
        } catch (Exception e) {
            log.error("Ошибка при создании топика {}", topicName);
        }
    }
}