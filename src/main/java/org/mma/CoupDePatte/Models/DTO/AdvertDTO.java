package org.mma.CoupDePatte.Models.DTO;

import java.util.Date;

public record AdvertDTO(
        Date eventDate,
        String description,
        String photoUrl,
        Boolean isTakeIn,
        Boolean isFound,
        String email,
        CityDTO cityDTO,
        PetDTO petDTO
) {
}
