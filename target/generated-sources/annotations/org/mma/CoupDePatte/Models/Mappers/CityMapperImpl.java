package org.mma.CoupDePatte.Models.Mappers;

import javax.annotation.processing.Generated;
import org.mma.CoupDePatte.Models.DTO.CityDTO;
import org.mma.CoupDePatte.Models.Entities.City;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-17T18:49:45+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class CityMapperImpl implements CityMapper {

    @Override
    public CityDTO toCityDTO(City city) {
        if ( city == null ) {
            return null;
        }

        CityDTO cityDTO = new CityDTO();

        cityDTO.setName( city.getName() );
        cityDTO.setZipCode( city.getZipCode() );

        return cityDTO;
    }

    @Override
    public City toEntity(CityDTO cityDTO) {
        if ( cityDTO == null ) {
            return null;
        }

        City city = new City();

        city.setName( cityDTO.getName() );
        city.setZipCode( cityDTO.getZipCode() );

        return city;
    }

    @Override
    public City partialUpdate(CityDTO cityDTO, City city) {
        if ( cityDTO == null ) {
            return city;
        }

        if ( cityDTO.getName() != null ) {
            city.setName( cityDTO.getName() );
        }
        if ( cityDTO.getZipCode() != null ) {
            city.setZipCode( cityDTO.getZipCode() );
        }

        return city;
    }
}
