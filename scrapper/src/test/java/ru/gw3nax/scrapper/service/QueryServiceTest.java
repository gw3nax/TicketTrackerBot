package ru.gw3nax.scrapper.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;
import ru.gw3nax.scrapper.dto.request.FlightRequest;
import ru.gw3nax.scrapper.entity.FlightQuery;
import ru.gw3nax.scrapper.entity.UserEntity;
import ru.gw3nax.scrapper.repository.FlightQueryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class QueryServiceTest {

    @Mock
    private FlightQueryRepository flightQueryRepository;

    @Mock
    private ConversionService conversionService;

    @Mock
    private UserService userService;

    @InjectMocks
    private QueryService queryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSearchQuery() {
        var flightRequest = FlightRequest.builder()
                .userId("1234") // Убедитесь, что userId указан
                .fromPlace("LED")
                .toPlace("SVO")
                .fromDate(LocalDate.of(2024, 12, 12))
                .toDate(LocalDate.of(2024, 12, 15))
                .currency("RUB")
                .price(BigDecimal.valueOf(5000))
                .build();
        var user = UserEntity.builder()
                .clientName("topic123")
                .userId("1234")
                .build();
        var flightQuery = FlightQuery.builder()
                .user(user)
                .fromPlace("LED")
                .toPlace("SVO")
                .currency("RUB")
                .price(BigDecimal.valueOf(5000))
                .build();

        // Мокаем вызовы методов
        when(userService.registerUser("1234", "test-header")).thenReturn(user);
        when(conversionService.convert(flightRequest, FlightQuery.class)).thenReturn(flightQuery);

        queryService.addSearchQuery(flightRequest, "test-header");

        // Проверяем вызовы моков
        verify(userService).registerUser("1234", "test-header");
        verify(conversionService).convert(flightRequest, FlightQuery.class);
        verify(flightQueryRepository).saveIfNotExist(flightQuery);
    }


    @Test
    void testDeleteSearchQuery() {
        var flightRequest = FlightRequest.builder()
                .userId("user123")
                .build();
        var user = UserEntity.builder()
                .userId("user123")
                .build();

        when(userService.findUserByUserId("user123")).thenReturn(user.getId());

        queryService.deleteSearchQuery(flightRequest);

        verify(userService).findUserByUserId("user123");
        verify(flightQueryRepository).deleteFlightQueryByUserId(user.getId());
    }

    @Test
    void testUpdateSearchQuery() {
        var flightRequest = FlightRequest.builder()
                .fromPlace("LED")
                .toPlace("SVO")
                .fromDate(LocalDate.of(2024, 12, 12))
                .toDate(LocalDate.of(2024, 12, 15))
                .currency("RUB")
                .price(BigDecimal.valueOf(5000))
                .userId("user123")
                .build();

        var user = UserEntity.builder()
                .userId("user123")
                .build();

        FlightQuery existingQuery = FlightQuery.builder()
                .id(1L)
                .fromPlace("LED")
                .toPlace("SVO")
                .build();

        FlightQuery updatedQuery = FlightQuery.builder()
                .id(1L)
                .fromPlace("LED")
                .toPlace("SVO")
                .build();

        when(userService.findUserByUserId("user123")).thenReturn(user.getId());
        when(flightQueryRepository.findFlightQueryByUserId(user.getId())).thenReturn(Optional.of(existingQuery));
        when(conversionService.convert(flightRequest, FlightQuery.class)).thenReturn(updatedQuery);

        queryService.updateSearchQuery(flightRequest);

        verify(userService).findUserByUserId("user123");
        verify(flightQueryRepository).findFlightQueryByUserId(user.getId());
        verify(conversionService).convert(flightRequest, FlightQuery.class);
        verify(flightQueryRepository).save(updatedQuery);

        assertThat(updatedQuery.getId()).isEqualTo(1L);
    }
}
