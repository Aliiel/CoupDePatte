package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Models.Entities.Advert;
import org.mma.CoupDePatte.Models.Repositories.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertServices {

    private final AdvertRepository advertRepository;

    @Autowired
    public AdvertServices(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    public Advert save(Advert advert) {
        return advertRepository.save(advert);
    }
}
