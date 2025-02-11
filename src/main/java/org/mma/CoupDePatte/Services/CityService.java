package org.mma.CoupDePatte.Services;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.mma.CoupDePatte.Models.Entities.City;
import org.mma.CoupDePatte.Models.Repositories.CityRepository;
import org.mma.CoupDePatte.Models.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final UserRepository userRepository;

    public City getUserById(Long id) {
        return userRepository.getUserById(id).getCity();
    }

    public City getByName(@NotBlank(message = "Précisez à proximité de quelle ville") String name) {
        //TODO add exception
        return cityRepository.findByName(name).orElseThrow();
    }
}
