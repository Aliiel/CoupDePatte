package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.AdvertDTO;
import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Models.DTO.FilterDTO;
import org.mma.CoupDePatte.Models.Entities.*;
import org.mma.CoupDePatte.Models.Mappers.AdvertMapper;
import org.mma.CoupDePatte.Models.Mappers.PetMapper;
import org.mma.CoupDePatte.Models.Repositories.AdvertRepository;
import org.mma.CoupDePatte.Models.Repositories.BreedRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class AdvertServices {
    AdvertRepository advertRep;
    PetServices petServ;
    AdvertMapper advMap;
    CityServices cityServ;
    UserServices userServ;
    PetMapper petMap;

    public AdvertServices(AdvertRepository advertRepository, PetServices petService,
                          CityServices cityService, UserServices userService, AdvertMapper advMapper,
                          BreedRepository breedRepository, PetMapper petMapper){
        this.advertRep= advertRepository;
        this.petServ = petService;
        this.cityServ = cityService;
        this.userServ=userService;
        this.advMap = advMapper;
        this.petMap=petMapper;
    }

    public ArrayList<Advert> findGoodList(FilterDTO filterDTO){
        //Partie du filtre obligatoire donc avec une information
        City city=cityServ.getByName(filterDTO.town());
        Date eventDate = filterDTO.eventDate();
        ArrayList<Advert> lstAdvertOK ;//Déclaration sans new ArrayList pour la charger en bloc dans if
        //et l'utiliser en dehors du if (portabilité)
        //fin filtre obligatoire

        //Récupère la liste des annonces "Trouvé" ou "Perdu" actives
        // en fonction de la date et de la ville renvoyées par le filtre
        // et conditionnée sur lien avec un des animaux correspondant aux autres filtres (petServ.getPetByFilter(filterDTO))
        //Utilisation de la requète dynamique (petServ.getPetByFilterSpec(filterDTO))
        if(filterDTO.isTrouve()) {
            lstAdvertOK = advertRep.findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundTrueAndPetInOrderByEventDateDesc
                    (city, eventDate, petServ.getPetByFilterSpec(filterDTO));
        }else {
            lstAdvertOK = advertRep.findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundFalseAndPetInOrderByEventDateDesc
                    (city, eventDate, petServ.getPetByFilterSpec(filterDTO));
        }
        return lstAdvertOK;

    }

    public AdvertResponseDTO getById(Long id) {
        Advert advert = advertRep.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce avec ID " + id + " non trouvée ou non active"));
        return advMap.AdvertToResponseDTO(advert);

    }

    public ArrayList<AdvertResponseDTO> getByFilter(FilterDTO filterDTO) {
        ArrayList<Advert> lstAdvert= findGoodList(filterDTO);
        if(lstAdvert.size()==0){
            new ResourceNotFoundException("Aucune annonce ne correspond à votre sélection");
        }

        ArrayList<AdvertResponseDTO> lstResponse = new ArrayList<>();
        for(Advert advert: lstAdvert){
            lstResponse.add(advMap.AdvertToResponseDTO(advert));
        }
        return lstResponse;

    }

    public String createAdvert(AdvertDTO advertDTO) {
        Date today = new Date();
        Advert advert = new Advert();
        advert.setCreationDate(today);
        advert.setUpdateDate(today);
        advert.setEventDate(advertDTO.eventDate());
        advert.setDescription(advertDTO.description());
        advert.setPhotoUrl(advertDTO.photoUrl());
        advert.setIsActive(true);
        advert.setIsTakeIn(advertDTO.isTakeIn());
        advert.setIsFound(advertDTO.isFound());
        advert.setUser(userServ.getById(advertDTO.userId()));
        advert.setCity(cityServ.getById(advertDTO.cityId()));
        advert.setPet(petServ.createPet(advertDTO.petDTO()));
        advertRep.save(advert);
        Long advertId = advert.getId();

        return "Votre annonce a bien été créée sous la référence "+Long.toString(advertId);
    }

    public AdvertResponseDTO updateAdvert(long id, AdvertDTO advertDTO) {
        Advert advert = advertRep.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce avec ID " + id + " non trouvée ou non active"));
        Date today = new Date();
        advert.setUpdateDate(today);
        if (advertDTO.eventDate() !=null) {
            advert.setEventDate(advertDTO.eventDate());
        }
        if (advertDTO.description() !=null) {
            advert.setDescription(advertDTO.description());
        }
        if (advertDTO.photoUrl() !=null) {
            advert.setPhotoUrl(advertDTO.photoUrl());
        }
        if (advertDTO.isTakeIn() !=null) {
        advert.setIsTakeIn(advertDTO.isTakeIn());
        }
        if (advertDTO.petDTO() !=null) {
            petServ.updatePet(advert.getPet().getId(),advertDTO.petDTO());
        }

        advertRep.save(advert);
        return advMap.AdvertToResponseDTO(advert);

    }
}
