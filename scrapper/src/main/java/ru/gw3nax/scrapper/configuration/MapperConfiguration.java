package ru.gw3nax.scrapper.configuration;

import org.mapstruct.*;

@MapperConfig(
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        injectionStrategy = InjectionStrategy.FIELD,
        implementationPackage = "<PACKAGE_NAME>.impl",
        implementationName = "<CLASS_NAME>Impl",
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface MapperConfiguration {
}