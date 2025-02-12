package org.mma.CoupDePatte.Models.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mma.CoupDePatte.Models.DTO.PetDTO;
import org.mma.CoupDePatte.Models.Entities.Pet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapStructMapper {

    PetDTO toPetDTO (Pet pet);

    Pet toPet (PetDTO petDTO);
}
