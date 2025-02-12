package org.mma.CoupDePatte.Services;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.CityDTO;
import org.mma.CoupDePatte.Models.Entities.City;
import org.mma.CoupDePatte.Models.Repositories.CityRepository;
import org.mma.CoupDePatte.Models.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public City getByName(@NotBlank(message = "Précisez à proximité de quelle ville") String name) {
        //TODO add exception
        return cityRepository.findByName(name).orElseThrow();
    }

    public City getCity(CityDTO cityDTO){
        // renvoie la city sans création si n'existe pas
        return cityRepository.findCity(cityDTO.getName(), cityDTO.getZipCode())
                .orElseThrow(() -> new ResourceNotFoundException("Ville " + cityDTO.getName()

                        +", code postal " + cityDTO.getZipCode()+" inconnue"));
    }
    public City getByDTO(CityDTO cityDTO){
        return (cityRepository.findCity(cityDTO.getName(), cityDTO.getZipCode())).orElse(createCity(cityDTO)) ;
        // renvoie la city et la crée si n'existe pas

    }
    public City createCity(CityDTO cityDTO){
        City city = new City();
        city.setName(cityDTO.getName());
        city.setZipCode(cityDTO.getZipCode());
        cityRepository.save(city);
          return city;
    }   
}