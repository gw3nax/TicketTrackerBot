package ru.gw3nax.scrapper.exception.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ErrorResponse(
        String message,
        String code,
        List<String> stackTrace
) {
}
