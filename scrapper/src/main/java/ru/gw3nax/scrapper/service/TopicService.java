package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final KafkaAdmin kafkaAdmin;

    public void createNewTopic(String header) {
        var topicName = header;
        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            NewTopic topic = new NewTopic(topicName, 1, (short) 1);
            adminClient.createTopics(Collections.singletonList(topic));
            System.out.println("Топик успешно создан: " + topicName);
        } catch (Exception e) {
            System.err.println("Ошибка при создании топика: " + e.getMessage());
        }
    }
}
