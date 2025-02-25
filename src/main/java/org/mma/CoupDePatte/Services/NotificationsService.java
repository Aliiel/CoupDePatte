package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Models.DTO.MsgDTO;
import org.mma.CoupDePatte.Models.Entities.Advert;
import org.mma.CoupDePatte.Models.Entities.User;
import org.mma.CoupDePatte.Models.Repositories.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationsService {

    private final SimpMessagingTemplate messagingTemplate;
    private final AdvertRepository advertRepository;


    @Autowired
    public NotificationsService(SimpMessagingTemplate messagingTemplate, AdvertRepository advertRepository) {
        this.messagingTemplate = messagingTemplate;
        this.advertRepository = advertRepository;
    }

    public void sendNewAdvertNotification(Advert advert) {
        if (Boolean.FALSE.equals(advert.getIsFound())) { // On ne notifie que si c'est un animal perdu
            Long breedId = advert.getPet().getBreed().getId(); // Récupère l'ID de la breed de l'animal perdu
            List<Advert> adverts = advertRepository.findUsersByMatchingFoundAdverts(advert.getCity().getId(), breedId);

            // Envoyer la notification à chaque utilisateur concerné

            List<User> users = adverts.stream().map(Advert::getUser).toList();
            for (User user : users) {
                messagingTemplate.convertAndSendToUser(
                        user.getId().toString(), "/queue/notifications", advert
                );
            }
        }
    }

    public void sendNewMsgNotification(MsgDTO msgDTO, Advert advert) {
        // Envoyer la notification au créateur de l'annonce pour laquelle on a envoyé un message
        User user=advert.getUser();
        messagingTemplate.convertAndSendToUser(
                    user.getId().toString(), "/queue/notifications", msgDTO
                    );
    }

    public void sendNewAnswerNotification(MsgDTO msgDTO, User user) {
        // Envoyer la notification au créateur de l'annonce pour laquelle on a envoyé un message
        messagingTemplate.convertAndSendToUser(
                user.getId().toString(), "/queue/notifications", msgDTO
                );
    }

    public void sendNewFoundAdvertNotification(Advert advert) {
        if (advert.getIsFound()) { // On ne notifie que si c'est un animal trouvé
            Long breedId = advert.getPet().getBreed().getId(); // Récupère l'ID de la breed de l'animal trouvé
            List<User> usersToNotify = advertRepository.findUsersByMatchingLostAdverts(advert.getCity().getId(), breedId);

            // Envoyer la notification à chaque utilisateur concerné
            for (User user : usersToNotify) {
                messagingTemplate.convertAndSendToUser(
                        user.getId().toString(),
                        "/queue/notifications",
                        advert
                );
            }
        }
    }
}
