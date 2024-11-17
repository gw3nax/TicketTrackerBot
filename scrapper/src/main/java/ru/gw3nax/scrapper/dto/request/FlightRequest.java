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
    private String userId;
    private String fromPlace;
    private String toPlace;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String currency;
    private BigDecimal price;
}
