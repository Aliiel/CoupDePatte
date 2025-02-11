package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.AdvertDTO;
import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Models.Entities.Advert;
import org.mma.CoupDePatte.Models.Mappers.AdvertMapper;
import org.mma.CoupDePatte.Models.Mappers.FilterMapper;
import org.mma.CoupDePatte.Models.Mappers.PetMapper;
import org.mma.CoupDePatte.Models.Repositories.AdvertRepository;
import org.mma.CoupDePatte.Models.Repositories.BreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdvertService {

    private final NotificationsService notificationsService;
    AdvertRepository advertRep;
    PetService petServ;
    FilterMapper filterMap;
    AdvertMapper advMap;
    CityService cityServ;
    UserService userServ;
    PetMapper petMap;

    @Autowired
    public AdvertService(AdvertRepository advertRepository, PetService petService,
                         CityService cityService, UserService userService, AdvertMapper advMapper, BreedRepository breedRepository, PetMapper petMapper, NotificationsService notificationsService) {
        this.advertRep = advertRepository;
        this.petServ = petService;
        this.cityServ = cityService;
        this.userServ = userService;
        this.advMap = advMapper;
        this.petMap = petMapper;
        this.notificationsService = notificationsService;
    }


    public AdvertResponseDTO getById(Long id) {
        Advert advert = advertRep.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce avec ID " + id + " non trouvée ou non active"));

        return advMap.AdvertToResponseDTO(advert);

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
        advert.setCity(cityServ.getUserById(advertDTO.cityId()));
        advert.setPet(petServ.createPet(advertDTO.petDTO()));
        advertRep.save(advert); // Persist l'annonce

        // Appeler la méthode pour envoyer des notifications

        notificationsService.sendNewAdvertNotification(advert);

        Long advertId = advert.getId();
        return "Votre annonce a bien été créée sous la référence " + advertId;
    }


    public AdvertResponseDTO updateAdvert(long id, AdvertDTO advertDTO) {
        Advert advert = advertRep.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce avec ID " + id + " non trouvée ou non active"));
        Date today = new Date();
        advert.setUpdateDate(today);
        if (advertDTO.eventDate() != null) {
            advert.setEventDate(advertDTO.eventDate());
        }
        if (advertDTO.description() != null) {
            advert.setDescription(advertDTO.description());
        }
        if (advertDTO.photoUrl() != null) {
            advert.setPhotoUrl(advertDTO.photoUrl());
        }
        if (advertDTO.isTakeIn() != null) {
            advert.setIsTakeIn(advertDTO.isTakeIn());
        }
        if (advertDTO.petDTO() != null) {
            petServ.updatePet(advertDTO.petDTO());
        }

        advertRep.save(advert);

        return new AdvertResponseDTO(
                advert.getEventDate(),
                advert.getDescription(),
                advert.getPhotoUrl(),
                advert.getIsActive(),
                advert.getIsTakeIn(),
                advert.getIsFound(),
                advert.getCity().getName(),
                petMap.petToResponseDTO(advert.getPet()),
                advert.getCreationDate(),
                advert.getUpdateDate()
        );
    }
}
