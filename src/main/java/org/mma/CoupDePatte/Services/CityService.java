package org.mma.CoupDePatte.Services;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.CityDTO;
import org.mma.CoupDePatte.Models.Entities.City;
import org.mma.CoupDePatte.Models.Mappers.CityMapper;
import org.mma.CoupDePatte.Models.Repositories.CityRepository;
import org.mma.CoupDePatte.Models.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;


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


    public City findOrCreateCity(CityDTO cityDTO){

        return cityRepository.findByNameIgnoreCaseAndZipCodeIgnoreCase(cityDTO.getName(), cityDTO.getZipCode())
                .orElseGet(() -> cityRepository.save(cityMapper.toEntity(cityDTO)));
    }
}