package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Exceptions.*;
import org.mma.CoupDePatte.Models.DTO.*;
import org.mma.CoupDePatte.Models.Mappers.PetMapper;
import org.mma.CoupDePatte.Models.Repositories.*;
import org.mma.CoupDePatte.Models.Entities.*;
import static org.mma.CoupDePatte.Models.Specifications.PetSpecification.*;

import org.mma.CoupDePatte.Models.Specifications.PetFilterSpecification;
import org.mma.CoupDePatte.Models.Specifications.SearchCriteria;
import org.mma.CoupDePatte.Models.Specifications.SearchOperation;
import org.springframework.stereotype.Service;
import static org.springframework.data.jpa.domain.Specification.*;

import java.util.ArrayList;

@Service
public class PetService {
    PetRepository petRep;
    BreedService breedServ;
    GenderService genderServ;
    SpecieService specieServ;
    PetMapper petMap;

    public PetService(PetRepository petRepository, PetMapper petMapper, BreedService breedService,
                      SpecieService specieService, GenderService genderService){
        this.petRep = petRepository;
        this.petMap= petMapper;
        this.breedServ = breedService;
        this.specieServ = specieService;
        this.genderServ=genderService;
    }

    public PetResponseDTO getPetById(Long id){
         Pet pet = petRep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal avec ID " + id + " non trouvé"));
         PetResponseDTO petRDTO=petMap.petToResponseDTO(pet);
        return petRDTO;
    }

       public ArrayList<Pet> getPetByFilterSpec(FilterDTO filterDTO){
        //Récupère la liste des animaux correspondant aux critères transmis dans le filtre

        ArrayList<Pet> lstPet;
        ArrayList<Breed> lstBreed;
        //Utilisation des requêtes dynamiques (cf Specifications et MetaModels packages)
        PetFilterSpecification petFilter= new PetFilterSpecification();

        Specie specie = specieServ.getByName(filterDTO.specie());
        lstBreed = breedServ.getBySpecie(specie);
        petFilter.add(new SearchCriteria("breed", SearchOperation.IN,lstBreed));
        if (filterDTO.breed()!=null) {
            Breed breed = breedServ.getByName(filterDTO.breed());
            petFilter.add(new SearchCriteria("breed", SearchOperation.EQUAL, breed));
        }
        if(filterDTO.gender()!=null) {
            Gender gender = genderServ.getByName(filterDTO.gender());
            petFilter.add(new SearchCriteria("gender", SearchOperation.EQUAL, gender));
        }
        if(filterDTO.eyes()!=null) {
            petFilter.add(new SearchCriteria("eyesColor", SearchOperation.EQUAL, filterDTO.eyes()));
        }
        if(filterDTO.coat()!=null) {
            petFilter.add(new SearchCriteria("coatColor", SearchOperation.EQUAL, filterDTO.coat()));
        }
        if(filterDTO.tattoo()){
            petFilter.add(new SearchCriteria("tattoo", SearchOperation.EQUAL,true));
        }else {
            petFilter.add(new SearchCriteria("tattoo", SearchOperation.EQUAL, false));
        }
        lstPet = petRep.findAll(petFilter);

        return lstPet;
    }

    public Pet createPet(PetDTO petDTO){
        Pet pet = new Pet();
        pet.setName(petDTO.name());
        pet.setBehavior(petDTO.behavior());
        pet.setEyesColor(petDTO.eyesColor());
        pet.setCoatColor(petDTO.coatColor());
        pet.setTattoo(petDTO.tattoo());
        pet.setIdentificationChip(petDTO.identificationChip());
        pet.setGender(genderServ.getById(petDTO.genderId()));
        pet.setBreed(breedServ.getById(petDTO.breedId()));

        petRep.save(pet);
        return pet;
    }

    public PetResponseDTO updateDTOPet(long id, PetDTO petDTO){
        Pet pet = updatePet(id, petDTO);
        return petMap.petToResponseDTO(pet);
    }

    public Pet updatePet(long id, PetDTO petDTO){
        Pet pet = petRep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal avec ID " + id + " non trouvé"));
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
        //boolean donc toujours une valeur (false par défaut)
        pet.setTattoo(petDTO.tattoo());
        pet.setIdentificationChip(petDTO.identificationChip());

        if (petDTO.genderId()!=0) {
            pet.setGender(genderServ.getById(petDTO.genderId()));
        }
        if (petDTO.breedId()!=0) {
            pet.setBreed(breedServ.getById(petDTO.breedId()));
        }

        petRep.save(pet);
        return pet;
    }
}
