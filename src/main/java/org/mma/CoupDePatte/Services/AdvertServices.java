package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Comparators.OrderByDate;
import org.mma.CoupDePatte.Exceptions.BusinessLogicException;
import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.AdvertDTO;
import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Models.DTO.FilterDTO;
import org.mma.CoupDePatte.Models.DTO.MsgDTO;
import org.mma.CoupDePatte.Models.Entities.*;
import org.mma.CoupDePatte.Models.Mappers.AdvertMapper;
import org.mma.CoupDePatte.Models.Mappers.MsgMapper;
import org.mma.CoupDePatte.Models.Mappers.PetMapper;
import org.mma.CoupDePatte.Models.Repositories.AdvertRepository;
import org.mma.CoupDePatte.Models.Repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

@Service
public class AdvertServices {
    AdvertRepository advertRep;
    PetServices petServ;
    AdvertMapper advMap;
    CityServices cityServ;
    UserServices userServ;
    MessageRepository msgRep;
    AnswerServices answerServ;
    MsgMapper msgMap;
    PetMapper petMap;
    String msgTrouveDefault;
    String msgPerduDefault;

    public AdvertServices(AdvertRepository advertRepository, PetServices petService,
                            CityServices cityService, UserServices userService, AdvertMapper advMapper,
                            PetMapper petMapper, MsgMapper msgMapper,
                            MessageRepository msgRepository, AnswerServices answerService){
        this.advertRep= advertRepository;
        this.msgRep= msgRepository;
        this.petServ = petService;
        this.cityServ = cityService;
        this.userServ=userService;
        this.answerServ=answerService;
        this.advMap = advMapper;
        this.msgMap = msgMapper;
        this.petMap=petMapper;
        this.msgTrouveDefault="Cette personne a peut-être trouvé votre animal";
        this.msgPerduDefault="Cette personne a perdu un animal ressemblant à celui que vous avez trouvé";
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

    public AdvertResponseDTO getDTOById(Long id) {
        Advert advert = advertRep.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce avec ID " + id + " non trouvée ou non active"));
        return advMap.AdvertToResponseDTO(advert);

    }
    public Advert getById(Long id) {
        Advert advert = advertRep.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce avec ID " + id + " non trouvée ou non active"));
        return advert;

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

    public void updAdvUnActive(long id) {
        Advert advert = advertRep.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce avec ID " + id + " non trouvée ou non active"));
        advert.setIsActive(false);
        advertRep.save(advert);

    }

    public void updAdvActive(long id) {
        Advert advert = advertRep.findByIdAndIsActiveFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce avec ID " + id + " non trouvée ou active"));
        advert.setIsActive(true);
        advertRep.save(advert);

    }

    public String createMsg(long id, MsgDTO msgDTO){
        if((msgDTO.email()== null) && (msgDTO.phone()==null)){
            new BusinessLogicException("Merci de saisir votre adresse email et/ou votre numéro de téléphone pour"+
                    " permettre à l'annonceur de vous contacter");
        }
        if(msgDTO.date()== null){
            new BusinessLogicException("Merci de préciser la date de l'événement");
        }
        if(msgDTO.isTakeIn()== null){
            new BusinessLogicException("Merci de préciser si l'animal est mis en sécurité");
        }

        Advert advert = advertRep.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce avec ID " + id + " non trouvée ou non active"));
        Message message=new Message();
        if (advert.getIsFound()) {
            //annonce Trouvé
            if (msgDTO.content() !=null) {
                message.setContent(msgDTO.content());
            }else{
                message.setContent(msgPerduDefault);
            }
        }else {
            //annonce Perdu
            if (msgDTO.content() !=null) {
                message.setContent(msgDTO.content());
            }else{
                message.setContent(msgTrouveDefault);
            }
        }
        if (msgDTO.email() !=null) {
            message.setEmail(msgDTO.email());
        }
        if (msgDTO.phone() !=null) {
            message.setPhone(msgDTO.phone());
        }
        if (msgDTO.phone() !=null) {
            message.setPhone(msgDTO.phone());
        }
        message.setIsTakeIn(msgDTO.isTakeIn());
        message.setDate(msgDTO.date());
        message.setAdvert(advert);
        msgRep.save(message);
        return "Votre message est bien envoyé";
    }

    public String createAnswer(long id, long usrId, MsgDTO msgDTO){
        if((msgDTO.email()== null) && (msgDTO.phone()==null)){
            new BusinessLogicException("Merci de saisir votre adresse email et/ou votre numéro de téléphone pour"+
                    " permettre à l'annonceur de vous contacter");
        }
        if(msgDTO.date()== null){
            new BusinessLogicException("Merci de préciser la date de l'événement");
        }
        if(msgDTO.isTakeIn()== null){
            new BusinessLogicException("Merci de préciser si l'animal est mis en sécurité");
        }

        Advert advert = advertRep.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce avec ID " + id + " non trouvée ou non active"));
        User user = userServ.findById(usrId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur avec ID " + id + " inconnu"));
        if (advert.getIsFound()) {
            //annonce Trouvé
            answerServ.createAnswer(msgDTO,advert,user,msgPerduDefault);
        }else {
            answerServ.createAnswer(msgDTO,advert,user,msgTrouveDefault);
        }
        return "Votre message est bien envoyé";

    }

    public ArrayList<MsgDTO> searchAnswer(long id){
        ArrayList<MsgDTO> lstMsgDTO = new ArrayList<MsgDTO>();
        ArrayList<Message> lstMsg = msgRep.findByAdvertOrderByDateDesc(getById(id));
        ArrayList<Answer> lstAnswer = answerServ.getByAdvert(getById(id));

        for(Answer answer:lstAnswer){
            lstMsgDTO.add(msgMap.answerToDTO(answer));
        }
        for(Message message:lstMsg){
            lstMsgDTO.add(msgMap.msgToDTO(message));
        }
        Collections.sort(lstMsgDTO, new OrderByDate());
        return lstMsgDTO;
    }
}
