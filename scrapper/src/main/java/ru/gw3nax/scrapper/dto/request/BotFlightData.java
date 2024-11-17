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
    String fromPlace;
    String toPlace;
    LocalDate fromDate;
    LocalDate toDate;
    String link;
}
