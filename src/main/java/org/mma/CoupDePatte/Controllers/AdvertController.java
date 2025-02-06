package org.mma.CoupDePatte.Controllers;

import org.mma.CoupDePatte.Services.AdvertServices;
import org.mma.CoupDePatte.Services.NotificationsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adverts")
public class AdvertController {

    private final AdvertServices advertServices;
    private final NotificationsServices notificationsServices;

    @Autowired
    public AdvertController(AdvertServices advertServices, NotificationsServices notificationsServices) {
        this.advertServices = advertServices;
        this.notificationsServices = notificationsServices;
    }

    @PostMapping("/advert")
    Public ResponseEntity<Advert> createAdvert(@RequestBody Advert advert) {
        Advert savedAdverts = advertServices.save(advert);
        notificationsServices.sendNewAdvertNotification(savedAdverts);
        return ResponseEntity.ok(savedAdverts);
    }



}
