package ru.gw3nax.scrapper.consumer;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.gw3nax.scrapper.dto.request.FlightRequest;
import ru.gw3nax.scrapper.entity.FlightQuery;
import ru.gw3nax.scrapper.repository.FlightQueryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@SpringBootTest
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class QueryConsumerIntegrationTest {

    @Container
    private static final KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.2"))
            .withEnv("KAFKA_LISTENERS", "PLAINTEXT://0.0.0.0:9092,BROKER://0.0.0.0:9093")
            .withEnv("KAFKA_ADVERTISED_LISTENERS", "PLAINTEXT://localhost:9092,BROKER://localhost:9093")
            .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true")
            .withExposedPorts(9092);

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private KafkaTemplate<String, FlightRequest> kafkaTemplate;

    @Autowired
    private FlightQueryRepository flightQueryRepository;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);

        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeEach
    public void setupKafkaTopic() throws Exception {
        try (AdminClient adminClient = AdminClient.create(Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers()))) {
            adminClient.createTopics(List.of(new NewTopic("queries", 1, (short) 1))).all().get();
        }
    }

    @Test
    public void testConsumerProcess() throws InterruptedException, ExecutionException {
        FlightRequest flightRequest = new FlightRequest();
        flightRequest.setUserId("user1");
        flightRequest.setFromPlace("NYC");
        flightRequest.setToPlace("LAX");
        flightRequest.setFromDate(LocalDate.now());
        flightRequest.setToDate(LocalDate.now().plusDays(5));
        flightRequest.setCurrency("USD");
        flightRequest.setPrice(BigDecimal.valueOf(200));
        kafkaTemplate.send("queries", flightRequest).get();

        List<FlightQuery> queries = flightQueryRepository.findAll();
        Assertions.assertFalse(queries.isEmpty(), "FlightQuery should be saved in the database");
        Assertions.assertEquals(1, queries.size(), "There should be exactly one record");
        FlightQuery savedQuery = queries.getFirst();
        Assertions.assertEquals("NYC", savedQuery.getFromPlace());
        Assertions.assertEquals("LAX", savedQuery.getToPlace());
    }
}
