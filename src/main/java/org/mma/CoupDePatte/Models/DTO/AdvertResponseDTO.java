package org.mma.CoupDePatte.Models.DTO;

import java.util.Date;

public record AdvertResponseDTO(
        Date eventDate,
        String description,
        String photoUrl,
        Boolean isActive,
        Boolean isTakeIn,
        Boolean isFound,
        CityDTO cityDTO,
        PetResponseDTO petRespDTO,
        Date creationDate,
        Date updateDate
) {
}
