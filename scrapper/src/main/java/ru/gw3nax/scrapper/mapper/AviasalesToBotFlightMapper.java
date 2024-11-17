package ru.gw3nax.scrapper.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import ru.gw3nax.scrapper.configuration.MapperConfiguration;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.dto.response.AviasalesResponseData;

@Mapper(config = MapperConfiguration.class)
public interface AviasalesToBotFlightMapper extends Converter<AviasalesResponseData, BotFlightData> {

    @Override
    @Mapping(source = "origin", target = "fromPlace")
    @Mapping(source = "destination", target = "toPlace")
    @Mapping(source = "departureAt", target = "departureAt")
    @Mapping(source = "link", target = "link")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "airline", target = "airline")
    BotFlightData convert(AviasalesResponseData aviasalesResponseData);
}