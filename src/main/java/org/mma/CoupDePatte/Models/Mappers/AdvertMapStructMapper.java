package org.mma.CoupDePatte.Models.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Models.Entities.Advert;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PetMapStructMapper.class, CityMapper.class})
public interface AdvertMapStructMapper {

    @Mapping(source = "pet", target = "petRespDTO")
    @Mapping(source = "city", target = "cityDTO")
    AdvertResponseDTO toAdvertReponseDTO(Advert advert);

    @Mapping(source = "petRespDTO", target = "pet")
    @Mapping(source = "cityDTO", target = "city")
    Advert toAdvert(AdvertResponseDTO advertReponseDTO);

    List<AdvertResponseDTO> toDTOList(List<Advert> adverts);
}
