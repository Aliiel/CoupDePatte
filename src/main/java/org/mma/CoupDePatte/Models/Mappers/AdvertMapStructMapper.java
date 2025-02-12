package org.mma.CoupDePatte.Models.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mma.CoupDePatte.Models.DTO.AdvertDTO;
import org.mma.CoupDePatte.Models.Entities.Advert;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PetMapStructMapper.class, CityMapper.class})
public interface AdvertMapStructMapper {

    @Mapping(source = "pet", target = "petDTO")
    @Mapping(source = "city", target = "cityDTO")
    AdvertDTO toAdvertDTO(Advert advert);

    @Mapping(source = "petDTO", target = "pet")
    @Mapping(source = "cityDTO", target = "city")
    Advert toAdvert(AdvertDTO advertDTO);
}
