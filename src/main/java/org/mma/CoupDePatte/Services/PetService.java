package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Exceptions.*;
import org.mma.CoupDePatte.Models.DTO.*;
import org.mma.CoupDePatte.Models.Repositories.*;
import org.mma.CoupDePatte.Models.Entities.*;

import org.springframework.stereotype.Service;

@Service
public class PetService {
    PetRepository petRep;
    GenderRepository genderRep;
    BreedRepository breedRep;

    public PetService(PetRepository petRepository, GenderRepository genderRepository,
                      BreedRepository breedRepository){
        this.petRep = petRepository;
        this.genderRep= genderRepository;
        this.breedRep= breedRepository;
    }

    public Pet getPetById(Long id){
         Pet pet = petRep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal avec ID " + id + " non trouvé"));
        return pet;
    }

    public Pet createPet(PetDTO petDTO){
        Pet pet = new Pet();
        pet.setName(petDTO.name());
        pet.setBehavior(petDTO.behavior());
        pet.setEyesColor(petDTO.eyesColor());
        pet.setCoatColor(petDTO.coatColor());
        pet.setTattoo(petDTO.tattoo());
        pet.setIdentificationChip(petDTO.identificationChip());
        pet.setGender(genderRep.findById(petDTO.genderId())
                .orElseThrow(()->new ResourceNotFoundException("Genre avec l'id: "+petDTO.genderId()+" inconnu")));
        pet.setBreed(breedRep.findById(petDTO.breedId())
                .orElseThrow(()->new ResourceNotFoundException("Race avec l'id: "+petDTO.breedId()+" inconnue")));

        petRep.save(pet);
        return pet;
    }

    public Pet updatePet(PetDTO petDTO){
        Pet pet = new Pet();
        if (petDTO.name()!=null) {
            pet.setName(petDTO.name());
        }
        if (petDTO.behavior()!=null) {
            pet.setBehavior(petDTO.behavior());
        }
        if (petDTO.eyesColor()!=null) {
            pet.setEyesColor(petDTO.eyesColor());
        }
        if (petDTO.coatColor()!=null) {
            pet.setCoatColor(petDTO.coatColor());
        }
        if (petDTO.tattoo()) {
            pet.setTattoo(petDTO.tattoo());
        }
        if (petDTO.identificationChip()) {
            pet.setIdentificationChip(petDTO.identificationChip());
        }
        if (petDTO.genderId()!=0) {
            pet.setGender(genderRep.findById(petDTO.genderId())
                .orElseThrow(()->new ResourceNotFoundException("Genre avec l'id: "+petDTO.genderId()+" inconnu")));
        }
        if (petDTO.breedId()!=0) {
            pet.setBreed(breedRep.findById(petDTO.breedId())
                    .orElseThrow(()->new ResourceNotFoundException("Race avec l'id: "+petDTO.breedId()+" inconnue")));
        }

        petRep.save(pet);
        return pet;
    }
}
