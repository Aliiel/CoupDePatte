package org.mma.CoupDePatte.Models.DTO;

public record PetDTO(
        String name,
        String behavior,
        String eyesColor,
        String coatColor,
        Boolean tattoo,
        Boolean identificationChip,
        long genderId,
        long breedId
) {
}
