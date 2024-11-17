package ru.gw3nax.scrapper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BotFlightRequest {
    String userId;
    String currency;
    BigDecimal price;
    List<BotFlightData> data;
}
