package org.mma.CoupDePatte.Services;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.CityDTO;
import org.mma.CoupDePatte.Models.Entities.City;
import org.mma.CoupDePatte.Models.Repositories.CityRepository;
import org.mma.CoupDePatte.Models.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public City getCity(CityDTO cityDTO){
        // renvoie la city sans création si n'existe pas
        return cityRepository.findByNameIgnoreCaseAndZipCodeIgnoreCase(cityDTO.getName(), cityDTO.getZipCode())
                .orElseThrow(() -> new ResourceNotFoundException("Ville " + cityDTO.getName()

                        +", code postal " + cityDTO.getZipCode()+" inconnue"));
    }
    public City getByDTO(CityDTO cityDTO){
        Optional<City> optionalCity = cityRepository.findByNameIgnoreCaseAndZipCodeIgnoreCase(cityDTO.getName(), cityDTO.getZipCode());
        return optionalCity.orElseGet(() -> createCity(cityDTO));
    }
    public City createCity(CityDTO cityDTO){
        City city = new City();
        city.setName(cityDTO.getName());
        city.setZipCode(cityDTO.getZipCode());
        cityRepository.save(city);
        return city;
    }   
}