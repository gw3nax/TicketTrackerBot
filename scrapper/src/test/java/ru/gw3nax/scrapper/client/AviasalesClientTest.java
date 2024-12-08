package ru.gw3nax.scrapper.client;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;
import ru.gw3nax.scrapper.entity.FlightQuery;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class AviasalesClientTest {

    private MockWebServer mockWebServer;
    private AviasalesClient aviasalesClient;

    @BeforeEach
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String mockBaseUrl = UriComponentsBuilder
                .fromHttpUrl(mockWebServer.url("/").toString())
                .toUriString();

        aviasalesClient = new AviasalesClient(mockBaseUrl, "test-token");
    }

    @AfterEach
    void teardown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testGetTickets() {
        String jsonResponse = """
            {
              "data": {
                "2024-12-07": {
                  "flight_number": "6826",
                  "link": "/search/LED0712SVO1?t=DP17336097001733615700000100LEDSVO_1d108b23cdc07840336d5bbeb6770bd5_1825",
                  "origin_airport": "LED",
                  "destination_airport": "SVO",
                  "departure_at": "2024-12-07T22:15:00+03:00",
                  "airline": "DP",
                  "destination": "MOW",
                  "origin": "LED",
                  "price": 1825,
                  "return_transfers": 0,
                  "duration": 100,
                  "duration_to": 100,
                  "duration_back": 0,
                  "transfers": 0
                }
              }
            }
        """;
        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200));

        FlightQuery query = FlightQuery.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .fromDate(LocalDate.of(2024,12,12))
                .currency("RUB")
                .build();
        var response = aviasalesClient.getTickets(query, "price");
        assertThat(response).isNotNull();
        assertThat(response.getData()).containsKey("2024-12-07");
        var flightData = response.getData().get("2024-12-07");
        assertThat(flightData.getPrice()).isEqualTo(BigDecimal.valueOf(1825));
        assertThat(flightData.getOrigin()).isEqualTo("LED");
        assertThat(flightData.getDestination()).isEqualTo("MOW");
    }
}
