package ru.gw3nax.scrapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AviasalesResponseData {
    private String origin;
    private String destination;
    private String link;
    private BigDecimal price;
    private Integer transfers;
    private LocalDate departureAt;
    private String airline;
}
