package org.mma.CoupDePatte.Models.DTO;

import jakarta.persistence.*;
import org.mma.CoupDePatte.Models.Entities.Breed;
import org.mma.CoupDePatte.Models.Entities.Gender;

public record PetDTO(
    String name,
    String behavior,
    String eyesColor,
    String coatColor,
    Boolean tattoo,
    Boolean identificationChip,
    long genderId,
    long breedId
){}
