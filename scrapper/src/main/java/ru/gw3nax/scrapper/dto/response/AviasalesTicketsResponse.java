package ru.gw3nax.scrapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AviasalesTicketsResponse {
    private Boolean success;
    private String currency;
    private Map<String, AviasalesResponseData> data;
}
