package org.mma.CoupDePatte.Models.Mappers;

import org.mma.CoupDePatte.Models.DTO.PetDTO;
import org.mma.CoupDePatte.Models.DTO.PetResponseDTO;
import org.mma.CoupDePatte.Models.Entities.Pet;
import org.mma.CoupDePatte.Services.BreedServices;
import org.mma.CoupDePatte.Services.GenderServices;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    GenderServices genderServ;
    BreedServices breedServ;


    public PetMapper (BreedServices breedServices, GenderServices genderServices){
        this.genderServ = genderServices;
        this.breedServ = breedServices;
    }

    public PetDTO petToDTO (Pet pet){
        return new PetDTO(
            pet.getName(),
            pet.getBehavior(),
            pet.getEyesColor(),
            pet.getCoatColor(),
            pet.getTattoo(),
            pet.getIdentificationChip(),
            pet.getGender().getId(),
            pet.getBreed().getId()
            );

    }

    public PetResponseDTO petToResponseDTO (Pet pet){
        return new PetResponseDTO(
                pet.getBreed().getSpecie().getName(),
                pet.getBreed().getName(),
                pet.getGender().getName(),
                pet.getName(),
                pet.getBehavior(),
                pet.getEyesColor(),
                pet.getCoatColor(),
                pet.getTattoo(),
                pet.getIdentificationChip()
        );

    }

    public Pet DTOToPet (PetDTO petDTO){
        Pet pet = new Pet();
        pet.setName(petDTO.name());
        pet.setBehavior(petDTO.behavior());
        pet.setEyesColor(petDTO.eyesColor());
        pet.setCoatColor(petDTO.coatColor());
        pet.setTattoo(petDTO.tattoo());
        pet.setIdentificationChip(petDTO.identificationChip());
        pet.setBreed(breedServ.getById(petDTO.breedId()));
        pet.setGender(genderServ.getById(petDTO.genderId()));

        return pet;
    }
}
