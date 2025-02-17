package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Models.DTO.NotificationMessageDTO;
import org.mma.CoupDePatte.Models.Entities.Advert;
import org.mma.CoupDePatte.Models.Entities.User;
import org.mma.CoupDePatte.Models.Repositories.AdvertRepository;
import org.mma.CoupDePatte.Models.Repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationsService {

    private final SimpMessagingTemplate messagingTemplate;
    private final AdvertRepository advertRepository;

    @Autowired
    public NotificationsService(SimpMessagingTemplate messagingTemplate, AdvertRepository advertRepository,
        MessageRepository messageRepository) {
        this.messagingTemplate = messagingTemplate;
        this.advertRepository = advertRepository;
    }

    public void sendNewAdvertNotification(Advert advert) {
        if (Boolean.FALSE.equals(advert.getIsFound())) { // On ne notifie que si c'est un animal perdu
            Long breedId = advert.getPet().getBreed().getId();
            List<Advert> adverts = advertRepository.findUsersByMatchingFoundAdverts(advert.getCity().getId(), breedId);

            // Créer le message de notification

            NotificationMessageDTO message = new NotificationMessageDTO(
                    "Nouvelle alerte : Animal perdu",
                    "Un animal perdu correspondant à vos critères a été signalé.",
                    advert.getId()
            );

            // Envoyer la notification à chaque utilisateur concerné

            List<User> users = adverts.stream().map(Advert::getUser).toList();
            for (User user : users) {
                messagingTemplate.convertAndSendToUser(
                        user.getId().toString(), "/user/queue/notifications", message
                );
            }
        }
    }

    public void sendNewFoundAdvertNotification(Advert advert) {
        if (advert.getIsFound()) { // On ne notifie que si c'est un animal trouvé
            Long breedId = advert.getPet().getBreed().getId();
            List<User> usersToNotify = advertRepository.findUsersByMatchingLostAdverts(advert.getCity().getId(), breedId);

            // Créer le message de notification

            NotificationMessageDTO message = new NotificationMessageDTO(
                    "Bonne nouvelle : Animal trouvé",
                    "Un animal trouvé correspond à vos critères a été signalé. !",
                    advert.getId()
            );

            // Envoyer la notification à chaque utilisateur concerné

            for (User user : usersToNotify) {
                messagingTemplate.convertAndSendToUser(
                        user.getId().toString(), "/user/queue/notifications", message
                );
            }
        }
    }
}
