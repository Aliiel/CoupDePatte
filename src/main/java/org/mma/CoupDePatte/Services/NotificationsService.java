package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Models.DTO.MsgDTO;
import org.mma.CoupDePatte.Models.Entities.Advert;
import org.mma.CoupDePatte.Models.Entities.Answer;
import org.mma.CoupDePatte.Models.Entities.Message;
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
    private final MessageRepository messageRepository;

    @Autowired
    public NotificationsService(SimpMessagingTemplate messagingTemplate, AdvertRepository advertRepository,
                                MessageRepository messageRepository) {
        this.messagingTemplate = messagingTemplate;
        this.advertRepository = advertRepository;
        this.messageRepository = messageRepository;
    }

    public void sendNewAdvertNotification(Advert advert) {
        // On ne notifie que si c'est un animal perdu
        if (Boolean.FALSE.equals(advert.getIsFound())) {
            Long breedId = advert.getPet().getBreed().getId();
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
}
