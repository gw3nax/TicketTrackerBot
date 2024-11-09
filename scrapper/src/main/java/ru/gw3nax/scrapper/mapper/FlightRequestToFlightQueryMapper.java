package ru.gw3nax.scrapper.mapper;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import ru.gw3nax.scrapper.configuration.MapperConfiguration;
import ru.gw3nax.scrapper.dto.request.FlightRequest;
import ru.gw3nax.scrapper.entity.FlightQuery;

@Mapper(config = MapperConfiguration.class)
public interface FlightRequestToFlightQueryMapper  extends Converter<FlightRequest,FlightQuery> {
    @Override
    FlightQuery convert(FlightRequest source);
}
