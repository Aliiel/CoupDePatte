package org.mma.CoupDePatte.Models.Mappers;

import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Models.Entities.Advert;
import org.springframework.stereotype.Service;

@Service
public class AdvertMapper {
    PetMapper petMap;
    CityMapper cityMap;

    public AdvertMapper(PetMapper petMapper, CityMapper cityMapper){
        this.petMap=petMapper;
        this.cityMap=cityMapper;
    }

    public AdvertResponseDTO AdvertToResponseDTO(Advert advert) {
        return new AdvertResponseDTO(
                advert.getEventDate(),
                advert.getDescription(),
                advert.getPhotoUrl(),
                advert.getIsActive(),
                advert.getIsTakeIn(),
                advert.getIsFound(),
                cityMap.toCityDTO(advert.getCity()),
                petMap.petToResponseDTO(advert.getPet()),
                advert.getCreationDate(),
                advert.getUpdateDate()
        );
    }

}
