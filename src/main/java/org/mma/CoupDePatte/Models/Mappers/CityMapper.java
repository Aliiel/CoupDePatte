package org.mma.CoupDePatte.Models.Mappers;

import org.mapstruct.*;
import org.mma.CoupDePatte.Models.DTO.CityDTO;
import org.mma.CoupDePatte.Models.DTO.PetDTO;
import org.mma.CoupDePatte.Models.Entities.City;
import org.mma.CoupDePatte.Models.Entities.Pet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CityMapper {

    CityDTO toCityDTO(City city);

    City toEntity(CityDTO cityDTO);


}
