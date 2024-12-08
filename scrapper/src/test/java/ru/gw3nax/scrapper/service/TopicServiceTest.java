package ru.gw3nax.scrapper.service;

import org.apache.kafka.clients.admin.AdminClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaAdmin;
import ru.gw3nax.scrapper.configuration.properties.KafkaClientTopicsProperties;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TopicServiceTest {

    @Mock
    private KafkaClientTopicsProperties kafkaClientsProperties;

    @Mock
    private KafkaAdmin kafkaAdmin;

    @InjectMocks
    private TopicService topicService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInit_createsTopics() {
        when(kafkaClientsProperties.getClientsProps()).thenReturn(
                Map.of("client1", "topic1", "client2", "topic2")
        );
        when(kafkaAdmin.getConfigurationProperties()).thenReturn(Map.of());
        topicService.init();
        verify(kafkaClientsProperties, atLeastOnce()).getClientsProps();
    }


    @Test
    void testCreateNewTopic_handlesException() {
        AdminClient adminClientMock = mock(AdminClient.class);
        doThrow(new RuntimeException("Test exception"))
                .when(adminClientMock).createTopics(any());

        when(kafkaAdmin.getConfigurationProperties()).thenReturn(Map.of());
    }
}
