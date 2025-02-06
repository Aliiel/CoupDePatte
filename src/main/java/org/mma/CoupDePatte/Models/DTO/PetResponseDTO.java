package org.mma.CoupDePatte.Models.DTO;

public record PetResponseDTO (
        String SpecieName,
        String breedName,
        String genderName,
        String name,
        String behavior,
        String eyesColor,
        String coatColor,
        Boolean tattoo,
        Boolean identificationChip
) {}
