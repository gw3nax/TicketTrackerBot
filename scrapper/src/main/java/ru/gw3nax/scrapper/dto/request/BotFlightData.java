package ru.gw3nax.scrapper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BotFlightData {
    private String fromPlace;
    private String toPlace;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String link;
}
