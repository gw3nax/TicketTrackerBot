package ru.gw3nax.scrapper.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.core.convert.converter.Converter;
import ru.gw3nax.scrapper.configuration.MapperConfiguration;
import ru.gw3nax.scrapper.dto.request.BotFlightData;
import ru.gw3nax.scrapper.dto.response.AviasalesResponseData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(config = MapperConfiguration.class)
public interface AviasalesToBotFlightMapper extends Converter<AviasalesResponseData, BotFlightData> {

    @Override
    @Mapping(source = "origin", target = "fromPlace")
    @Mapping(source = "destination", target = "toPlace")
    @Mapping(source = "departureAt", target = "departureAt", qualifiedByName = "mapDepartureAt")
    @Mapping(source = "link", target = "link")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "airline", target = "airline")
    BotFlightData convert(AviasalesResponseData aviasalesResponseData);

    @Named("mapDepartureAt")
    default LocalDate mapDepartureAt(String departureAt) {
        if (departureAt == null || departureAt.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        return LocalDate.parse(departureAt, formatter);
    }
}
