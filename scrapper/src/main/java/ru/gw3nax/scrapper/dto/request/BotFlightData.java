package ru.gw3nax.scrapper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BotFlightData {
    private String fromPlace;
    private String toPlace;
    private LocalDateTime departureAt;
    private BigDecimal price;
    private String airline;
    private String link;
}
