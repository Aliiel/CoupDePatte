package org.mma.CoupDePatte.Models.DTO;

import java.util.Date;

public record AdvertDTO(
        Date eventDate,
        String description,
        String photoUrl,
        Boolean isActive,
        Boolean isTakeIn,
        Boolean isFound,
        Long userId,
        Long cityId,
        PetDTO petDTO
) {
}
