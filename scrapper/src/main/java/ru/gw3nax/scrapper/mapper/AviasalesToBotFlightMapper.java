package ru.gw3nax.scrapper.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import ru.gw3nax.scrapper.configuration.MapperConfiguration;
import ru.gw3nax.scrapper.dto.request.BotFlightResponse;
import ru.gw3nax.scrapper.dto.response.AviasalesResponseData;

@Mapper(config = MapperConfiguration.class)
public interface AviasalesToBotFlightMapper extends Converter<AviasalesResponseData, BotFlightResponse> {

    @Override
    @Mapping(source = "origin", target = "fromPlace")
    @Mapping(source = "destination", target = "toPlace")
    @Mapping(source = "departureAt", target = "fromDate")
    @Mapping(source = "link", target = "link")
    BotFlightResponse convert(AviasalesResponseData aviasalesResponseData);
}