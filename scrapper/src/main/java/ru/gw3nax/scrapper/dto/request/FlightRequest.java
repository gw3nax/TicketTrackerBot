package ru.gw3nax.scrapper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightRequest {
    String userId;
    String fromPlace;
    String toPlace;
    LocalDate fromDate;
    LocalDate toDate;
    String currency;
    BigDecimal price;
}
