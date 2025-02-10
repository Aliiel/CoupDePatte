package org.mma.CoupDePatte.Models.Mappers;

import org.mma.CoupDePatte.Models.DTO.FilterDTO;
import org.mma.CoupDePatte.Models.Entities.*;
import org.mma.CoupDePatte.Models.Repositories.AdvertRepository;
import org.mma.CoupDePatte.Models.Repositories.PetRepository;

import java.util.ArrayList;
import java.util.Date;

public class FilterMapper {
    AdvertRepository advertRep;
    SpecieService specieServ;
    BreedService breedServ;
    CityService cityServ;
    GenderService genderServ;
    PetRepository petRep;

    public FilterMapper(AdvertRepository advertRepository, SpecieService specieService, CityService cityService,
                        GenderService genderService, BreedService breedService, PetRepository petRepository){
        this.specieServ=specieService;
        this.breedServ=breedService;
        this.cityServ = cityService;
        this.genderServ = genderService;
        this.advertRep= advertRepository;
        this.petRep = petRepository;
    }
    public ArrayList<Advert> findGoodList(FilterDTO filterDTO){
        //Partie du filtre obligatoire donc avec une information
        City city=cityServ.getByName(filterDTO.town());
        Date eventDate = filterDTO.eventDate();
        Specie specie = specieServ.getByName(filterDTO.specie());
        ArrayList<Advert> lstAdvertOK ;//Déclaration sans new ArrayList pour la charger en bloc dans if
        ArrayList<Pet> lstPet;          //et l'utiliser en dehors du if (portabilité)
        //fin filtre obligatoire


        //Récupère la liste des animaux correspondant aux critères transmis dans le filtre
        if (filterDTO.breed()!=null){
            Breed breed = breedServ.getByName(filterDTO.breed());
            if(filterDTO.gender()!=null){
                Gender gender = genderServ.getByName(filterDTO.gender());
                if(filterDTO.eyes()!=null){
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndBreedAndGenderAndEyesColorAndCoatColorAndTattooTrue
                                    (specie,breed,gender,filterDTO.eyes(),filterDTO.coat());
                        }else {
                            lstPet = petRep.findBySpecieAndBreedAndGenderAndEyesColorAndCoatColorAndTattooFalse
                                    (specie,breed,gender,filterDTO.eyes(),filterDTO.coat());
                        }
                    }else{
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndBreedAndGenderAndEyesColorAndTattooTrue(specie,breed,gender,filterDTO.eyes());
                        }else {
                            lstPet = petRep.findBySpecieAndBreedAndGenderAndEyesColorAndTattooFalse(specie,breed,gender,filterDTO.eyes());
                        }
                    }
                }else{
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndBreedAndGenderAndCoatColorAndTattooTrue
                                    (specie,breed,gender,filterDTO.coat());
                        }else {
                            lstPet = petRep.findBySpecieAndBreedAndGenderAndCoatColorAndTattooFalse
                                    (specie,breed,gender,filterDTO.coat());
                        }
                    }else {
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndBreedAndGenderAndTattooTrue
                                    (specie, breed, gender);
                        }else {
                            lstPet = petRep.findBySpecieAndBreedAndGenderAndTattooFalse
                                    (specie, breed, gender);
                        }
                    }
                }
            }else{
                if(filterDTO.eyes()!=null){
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndBreedAndEyesColorAndCoatColorAndTattooTrue
                                    (specie,breed,filterDTO.eyes(),filterDTO.coat());
                        }else {
                            lstPet = petRep.findBySpecieAndBreedAndEyesColorAndCoatColorAndTattooFalse
                                    (specie,breed,filterDTO.eyes(),filterDTO.coat());
                        }
                    }else{
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndBreedAndEyesColorAndTattooTrue(specie,breed,filterDTO.eyes());
                        }else {
                            lstPet = petRep.findBySpecieAndBreedAndEyesColorAndTattooFalse(specie,breed,filterDTO.eyes());
                        }
                    }
                }else{
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndBreedAndCoatColorAndTattooTrue(specie,breed,filterDTO.coat());
                        }else {
                            lstPet = petRep.findBySpecieAndBreedAndCoatColorAndTattooFalse(specie,breed,filterDTO.coat());
                        }
                    }else {
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndBreedAndTattooTrue(specie, breed);
                        }else {
                            lstPet = petRep.findBySpecieAndBreedAndTattooFalse(specie, breed);
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
                            lstPet = petRep.findBySpecieAndGenderAndEyesColorAndCoatColorAndTattooTrue
                                    (specie,gender,filterDTO.eyes(),filterDTO.coat());
                        }else {
                            lstPet = petRep.findBySpecieAndGenderAndEyesColorAndCoatColorAndTattooFalse
                                    (specie,gender,filterDTO.eyes(),filterDTO.coat());
                        }
                    }else{
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndGenderAndEyesColorAndTattooTrue(specie,gender,filterDTO.eyes());
                        }else {
                            lstPet = petRep.findBySpecieAndGenderAndEyesColorAndTattooFalse(specie,gender,filterDTO.eyes());
                        }
                    }
                }else{
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndGenderAndCoatColorAndTattooTrue(specie,gender,filterDTO.coat());
                        }else {
                            lstPet = petRep.findBySpecieAndGenderAndCoatColorAndTattooFalse(specie,gender,filterDTO.coat());
                        }
                    }else {
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndGenderAndTattooTrue(specie, gender);
                        }else {
                            lstPet = petRep.findBySpecieAndGenderAndTattooFalse(specie, gender);
                        }
                    }
                }
            }else{
                if(filterDTO.eyes()!=null){
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndEyesColorAndCoatColorAndTattooTrue
                                    (specie,filterDTO.eyes(),filterDTO.coat());
                        }else {
                            lstPet = petRep.findBySpecieAndEyesColorAndCoatColorAndTattooFalse
                                    (specie,filterDTO.eyes(),filterDTO.coat());
                        }
                    }else{
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndEyesColorAndTattooTrue(specie,filterDTO.eyes());
                        }else {
                            lstPet = petRep.findBySpecieAndEyesColorAndTattooFalse(specie,filterDTO.eyes());
                        }
                    }
                }else{
                    if(filterDTO.coat()!=null){
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndCoatColorAndTattooTrue(specie,filterDTO.coat());
                        }else {
                            lstPet = petRep.findBySpecieAndCoatColorAndTattooFalse(specie,filterDTO.coat());
                        }
                    }else {
                        if(filterDTO.tattoo()){
                            lstPet = petRep.findBySpecieAndTattooTrue(specie);
                        }else {
                            lstPet = petRep.findBySpecieAndTattooFalse(specie);
                        }
                    }
                }
            }
        }

        //Récupère la liste des annonces "Trouvé" ou "Perdu" actives
        // en fonction de la date et de la ville renvoyées par le filtre
        // et conditionnée sur lien avec un des animaux corrspondant aux autres filtres (lstPet)
        if(filterDTO.isTrouve()) {
            lstAdvertOK = advertRep.findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundTrueAndPetIn
                    (city, eventDate, lstPet);
        }else {
            lstAdvertOK = advertRep.findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundFalseAndPetIn
                    (city, eventDate, lstPet);
        }

        /*
        //Récupère la liste des annonces "Trouvé" ou "Perdu" active en fonction de la date et de la ville renvoyées par le filtre
        if(filterDTO.isTrouve()) {
            ArrayList<Advert> lstAdvert = advertRep.findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundTrue(city, eventDate);
        }else {
            ArrayList<Advert> lstAdvert = advertRep.findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundFalse(city, eventDate);
        }

        //Charge la liste à renvoyer annonce OK si contient l'un des animaux récupérés
        for(Advert advert: lstAdvert){
            if advert.getPet
        }
        */


        return lstAdvertOK;

    }
}
