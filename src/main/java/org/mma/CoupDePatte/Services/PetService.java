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

    /* pas utilisé dans ce programme
    public ArrayList<Pet> getPetByFilter(FilterDTO filterDTO){
        ArrayList<Pet> lstPet;
        ArrayList<Breed> lstBreed;

        Specie specie = specieServ.getByName(filterDTO.specie());
        lstBreed = breedServ.getBySpecie(specie);

        //Récupère la liste des animaux correspondant aux critères transmis dans le filtre
        if (filterDTO.breed()!=null){
            Breed breed = breedServ.getByName(filterDTO.breed());
            if(filterDTO.gender()!=null){
                Gender gender = genderServ.getByName(filterDTO.gender());
                if(filterDTO.eyes()!=null){
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndBreedAndGenderAndEyesColorAndCoatColorAndTattooTrue
                                    (lstBreed,breed,gender,filterDTO.eyes(),filterDTO.coat());
                        }else {
                            lstPet = petRep.findByBreedInAndBreedAndGenderAndEyesColorAndCoatColorAndTattooFalse
                                    (lstBreed,breed,gender,filterDTO.eyes(),filterDTO.coat());
                        }
                    }else{
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndBreedAndGenderAndEyesColorAndTattooTrue
                                    (lstBreed,breed,gender,filterDTO.eyes());
                        }else {
                            lstPet = petRep.findByBreedInAndBreedAndGenderAndEyesColorAndTattooFalse
                                    (lstBreed,breed,gender,filterDTO.eyes());
                        }
                    }
                }else{
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndBreedAndGenderAndCoatColorAndTattooTrue
                                    (lstBreed,breed,gender,filterDTO.coat());
                        }else {
                            lstPet = petRep.findByBreedInAndBreedAndGenderAndCoatColorAndTattooFalse
                                    (lstBreed,breed,gender,filterDTO.coat());
                        }
                    }else {
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndBreedAndGenderAndTattooTrue
                                    (lstBreed, breed, gender);
                        }else {
                            lstPet = petRep.findByBreedInAndBreedAndGenderAndTattooFalse
                                    (lstBreed, breed, gender);
                        }
                    }
                }
            }else{
                if(filterDTO.eyes()!=null){
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndBreedAndEyesColorAndCoatColorAndTattooTrue
                                    (lstBreed,breed,filterDTO.eyes(),filterDTO.coat());
                        }else {
                            lstPet = petRep.findByBreedInAndBreedAndEyesColorAndCoatColorAndTattooFalse
                                    (lstBreed,breed,filterDTO.eyes(),filterDTO.coat());
                        }
                    }else{
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndBreedAndEyesColorAndTattooTrue(lstBreed,breed,filterDTO.eyes());
                        }else {
                            lstPet = petRep.findByBreedInAndBreedAndEyesColorAndTattooFalse(lstBreed,breed,filterDTO.eyes());
                        }
                    }
                }else{
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndBreedAndCoatColorAndTattooTrue(lstBreed,breed,filterDTO.coat());
                        }else {
                            lstPet = petRep.findByBreedInAndBreedAndCoatColorAndTattooFalse(lstBreed,breed,filterDTO.coat());
                        }
                    }else {
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndBreedAndTattooTrue(lstBreed, breed);
                        }else {
                            lstPet = petRep.findByBreedInAndBreedAndTattooFalse(lstBreed, breed);
                        }
                    }
                }
            }
        }else{
            if(filterDTO.gender()!=null){
                Gender gender = genderServ.getByName(filterDTO.gender());
                if(filterDTO.eyes()!=null){
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndGenderAndEyesColorAndCoatColorAndTattooTrue
                                    (lstBreed,gender,filterDTO.eyes(),filterDTO.coat());
                        }else {
                            lstPet = petRep.findByBreedInAndGenderAndEyesColorAndCoatColorAndTattooFalse
                                    (lstBreed,gender,filterDTO.eyes(),filterDTO.coat());
                        }
                    }else{
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndGenderAndEyesColorAndTattooTrue(lstBreed,gender,filterDTO.eyes());
                        }else {
                            lstPet = petRep.findByBreedInAndGenderAndEyesColorAndTattooFalse(lstBreed,gender,filterDTO.eyes());
                        }
                    }
                }else{
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndGenderAndCoatColorAndTattooTrue(lstBreed,gender,filterDTO.coat());
                        }else {
                            lstPet = petRep.findByBreedInAndGenderAndCoatColorAndTattooFalse(lstBreed,gender,filterDTO.coat());
                        }
                    }else {
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndGenderAndTattooTrue(lstBreed, gender);
                        }else {
                            lstPet = petRep.findByBreedInAndGenderAndTattooFalse(lstBreed, gender);
                        }
                    }
                }
            }else{
                if(filterDTO.eyes()!=null){
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndEyesColorAndCoatColorAndTattooTrue
                                    (lstBreed,filterDTO.eyes(),filterDTO.coat());
                        }else {
                            lstPet = petRep.findByBreedInAndEyesColorAndCoatColorAndTattooFalse
                                    (lstBreed,filterDTO.eyes(),filterDTO.coat());
                        }
                    }else{
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndEyesColorAndTattooTrue(lstBreed,filterDTO.eyes());
                        }else {
                            lstPet = petRep.findByBreedInAndEyesColorAndTattooFalse(lstBreed,filterDTO.eyes());
                        }
                    }
                }else{
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndCoatColorAndTattooTrue(lstBreed,filterDTO.coat());
                        }else {
                            lstPet = petRep.findByBreedInAndCoatColorAndTattooFalse(lstBreed,filterDTO.coat());
                        }
                    }else {
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findByBreedInAndTattooTrue(lstBreed);
                        }else {
                            lstPet = petRep.findByBreedInAndTattooFalse(lstBreed);
                        }
                    }
                }
            }
        }
        return lstPet;
    }
    */

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
