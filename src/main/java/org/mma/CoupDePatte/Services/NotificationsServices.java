package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Models.Entities.Advert;
import org.mma.CoupDePatte.Models.Entities.User;
import org.mma.CoupDePatte.Models.Repositories.AdvertRepository;
import org.mma.CoupDePatte.Models.Repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NotificationsServices {

    private final SimpMessagingTemplate messagingTemplate;
    private final AdvertRepository advertRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public NotificationsServices(SimpMessagingTemplate messagingTemplate, AdvertRepository advertRepository,
        MessageRepository messageRepository) {
        this.messagingTemplate = messagingTemplate;
        this.advertRepository = advertRepository;
        this.messageRepository = messageRepository;
    }

    public void sendNewAdvertNotification(Advert advert) {
        if (!advert.getIsFound()) { // On ne notifie que si c'est un animal perdu
            Long breedId = advert.getPet().getBreed().getId(); // Récupère l'ID de la breed de l'animal perdu
            List<User> advertUsers = advertRepository.findUsersByMatchingFoundAdverts(advert.getCity().getId(), breedId);
            List<User> messageUsers = messageRepository.findUsersByMatchingFoundMessages(advert.getCity().getId(), breedId);

            // Fusionner les listes et éviter les doublons

            Set<User> usersToNotify = new HashSet<>(advertUsers);
            usersToNotify.addAll(messageUsers);

            // Envoyer la notification à chaque utilisateur concerné

            for (User user : usersToNotify) {
                messagingTemplate.convertAndSendToUser(
                    user.getId().toString(), "/queue/notifications", advert
                );
            }
        }
    }

}
