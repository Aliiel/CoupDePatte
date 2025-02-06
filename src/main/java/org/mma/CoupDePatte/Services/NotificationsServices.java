package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Models.Entities.Advert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationsServices {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationsServices(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNewAdvertNotification(Advert advert) {
        messagingTemplate.convertAndSend("/topic/adverts", advert);
    }
}
