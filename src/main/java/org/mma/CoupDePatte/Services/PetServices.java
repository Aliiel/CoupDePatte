package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Exceptions.*;
import org.mma.CoupDePatte.Models.DTO.*;
import org.mma.CoupDePatte.Models.Mappers.PetMapper;
import org.mma.CoupDePatte.Models.Repositories.*;
import org.mma.CoupDePatte.Models.Entities.*;
import static org.mma.CoupDePatte.Models.Specifications.PetSpecification.*;

import org.springframework.stereotype.Service;
import static org.springframework.data.jpa.domain.Specification.*;

import java.util.ArrayList;

@Service
public class PetServices {
    PetRepository petRep;
    BreedServices breedServ;
    GenderServices genderServ;
    SpecieServices specieServ;
    PetMapper petMap;

    public PetServices(PetRepository petRepository, PetMapper petMapper, BreedServices breedService,
                       SpecieServices specieService,GenderServices genderService){
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
        //Utilisation des requêtes dynamiques (cf Specifications et MetaModels packages)
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
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed).and(genderEqual(gender)
                                    .and(eyesEqual(filterDTO.eyes()).and(coatEqual(filterDTO.coat()).and(tattooTrue()
                                    )))))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed).and(genderEqual(gender)
                                    .and(eyesEqual(filterDTO.eyes()).and(coatEqual(filterDTO.coat()).and(tattooFalse()
                                    )))))));
                        }
                    }else{
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed).and(genderEqual(gender)
                                    .and(eyesEqual(filterDTO.eyes()).and(tattooTrue()
                                    ))))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed).and(genderEqual(gender)
                                    .and(eyesEqual(filterDTO.eyes()).and(tattooFalse()
                                    ))))));
                        }
                    }
                }else{
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed).and(genderEqual(gender)
                                    .and(coatEqual(filterDTO.coat()).and(tattooTrue()
                                    ))))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed).and(genderEqual(gender)
                                    .and(coatEqual(filterDTO.coat()).and(tattooFalse()
                                    ))))));
                        }
                    }else {
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed).and(genderEqual(gender)
                                    .and(tattooTrue()
                                    )))));

                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed).and(genderEqual(gender)
                                    .and(tattooFalse()
                                    )))));
                        }
                    }
                }
            }else{
                if(filterDTO.eyes()!=null){
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed)
                                    .and(eyesEqual(filterDTO.eyes()).and(coatEqual(filterDTO.coat()).and(tattooTrue()
                                    ))))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed)
                                    .and(eyesEqual(filterDTO.eyes()).and(coatEqual(filterDTO.coat()).and(tattooFalse()
                                    ))))));
                        }
                    }else{
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed)
                                    .and(eyesEqual(filterDTO.eyes()).and(tattooTrue()
                                    )))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed)
                                    .and(eyesEqual(filterDTO.eyes()).and(tattooFalse()
                                    )))));
                        }
                    }
                }else{
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed)
                                    .and(coatEqual(filterDTO.coat()).and(tattooTrue()
                                    )))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed)
                                    .and(coatEqual(filterDTO.coat()).and(tattooFalse()
                                    )))));
                        }
                    }else {
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed).and(tattooTrue()))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(breedEqual(breed).and(tattooFalse()))));
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
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(genderEqual(gender)
                                    .and(eyesEqual(filterDTO.eyes()).and(coatEqual(filterDTO.coat()).and(tattooTrue()
                                    ))))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(genderEqual(gender)
                                    .and(eyesEqual(filterDTO.eyes()).and(coatEqual(filterDTO.coat()).and(tattooFalse()
                                    ))))));
                        }
                    }else{
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(genderEqual(gender)
                                    .and(eyesEqual(filterDTO.eyes()).and(tattooTrue()
                                    )))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(genderEqual(gender)
                                    .and(eyesEqual(filterDTO.eyes()).and(tattooFalse()
                                    )))));
                        }
                    }
                }else{
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(genderEqual(gender)
                                    .and(coatEqual(filterDTO.coat()).and(tattooTrue()
                                    )))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(genderEqual(gender)
                                    .and(coatEqual(filterDTO.coat()).and(tattooFalse()
                                    )))));
                        }
                    }else {
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(genderEqual(gender).and(tattooTrue()))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(genderEqual(gender).and(tattooTrue()))));
                        }
                    }
                }
            }else{
                if(filterDTO.eyes()!=null){
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed)
                                    .and(eyesEqual(filterDTO.eyes()).and(coatEqual(filterDTO.coat()).and(tattooTrue()
                                    )))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed)
                                    .and(eyesEqual(filterDTO.eyes()).and(coatEqual(filterDTO.coat()).and(tattooFalse()
                                    )))));
                        }
                    }else{
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(eyesEqual(filterDTO.eyes()).and(tattooTrue()
                                    ))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(eyesEqual(filterDTO.eyes()).and(tattooFalse()
                                    ))));
                        }
                    }
                }else{
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(coatEqual(filterDTO.coat()).and(tattooTrue()
                                    ))));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(coatEqual(filterDTO.coat()).and(tattooFalse()
                                    ))));
                        }
                    }else {
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(tattooTrue())));
                        }else {
                            lstPet = petRep.findAll(where(breedIn(lstBreed).and(tattooFalse())));
                        }
                    }
                }
            }
        }
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
        if (petDTO.tattoo()) {
            pet.setTattoo(petDTO.tattoo());
        }
        if (petDTO.identificationChip()) {
            pet.setIdentificationChip(petDTO.identificationChip());
        }
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
