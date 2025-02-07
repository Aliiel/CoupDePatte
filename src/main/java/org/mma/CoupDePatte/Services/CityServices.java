package org.mma.CoupDePatte.Services;

import jakarta.validation.constraints.NotBlank;
import org.mma.CoupDePatte.Models.Entities.City;
import org.mma.CoupDePatte.Models.Repositories.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityServices {

    private final CityRepository cityRepository;

    public CityServices(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getUserById(Long id) {
        return cityRepository.getUserById(id);
    }

    public City getByName(@NotBlank(message = "Précisez à proximité de quelle ville") String name) {
        cityRepository.getCityByName();
        return null;
    }
}
