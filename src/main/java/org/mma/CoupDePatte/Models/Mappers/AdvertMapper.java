package org.mma.CoupDePatte.Models.Mappers;

import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Models.Entities.Advert;
import org.springframework.stereotype.Component;

@Component
public class AdvertMapper {
    PetMapper petMap;

    public AdvertMapper(PetMapper petMapper) {
        this.petMap = petMapper;
    }

    public AdvertResponseDTO AdvertToResponseDTO(Advert advert) {
        return new AdvertResponseDTO(
                advert.getEventDate(),
                advert.getDescription(),
                advert.getPhotoUrl(),
                advert.getIsActive(),
                advert.getIsTakeIn(),
                advert.getIsFound(),
                advert.getCity().getName(),
                petMap.petToResponseDTO(advert.getPet()),
                advert.getCreationDate(),
                advert.getUpdateDate()
        );
    }

}
