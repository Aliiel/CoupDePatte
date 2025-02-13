package org.mma.CoupDePatte.Models.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mma.CoupDePatte.Models.DTO.PetResponseDTO;
import org.mma.CoupDePatte.Models.Entities.Pet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapStructMapper {

    PetResponseDTO toPetResponseDTO (Pet pet);

    Pet toPet (PetResponseDTO petResponseDTO);
}
