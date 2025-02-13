package org.mma.CoupDePatte.Services;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.CityDTO;
import org.mma.CoupDePatte.Models.Entities.City;
import org.mma.CoupDePatte.Models.Mappers.CityMapper;
import org.mma.CoupDePatte.Models.Repositories.CityRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public City getByName(@NotBlank(message = "Précisez à proximité de quelle ville") String name) {
        //TODO add exception
        return cityRepository.findByName(name).orElseThrow();
    }

    public City getCity(CityDTO cityDTO){
        // renvoie la city sans création si n'existe pas
        return cityRepository.findByNameAndZipCodeIgnoreCase(cityDTO.getName(), cityDTO.getZipCode())
                .orElseThrow(() -> new ResourceNotFoundException("Ville " + cityDTO.getName()

                        +", code postal " + cityDTO.getZipCode()+" inconnue"));
    }
    public City getByDTO(CityDTO cityDTO){
        return (cityRepository.findByNameAndZipCodeIgnoreCase(cityDTO.getName(), cityDTO.getZipCode())).orElse(createCity(cityDTO)) ;
        // renvoie la city et la crée si n'existe pas

    }
    public City createCity(CityDTO cityDTO){
        City city = new City();
        city.setName(cityDTO.getName());
        city.setZipCode(cityDTO.getZipCode());
        cityRepository.save(city);
          return city;
    }


    public CityDTO findOrCreateCity(CityDTO cityDTO){

        log.info("findOrCreateCity cityDTO = " + cityDTO);

        City city = cityRepository.findByNameAndZipCodeIgnoreCase(cityDTO.getName(), cityDTO.getZipCode())
                .orElseGet(() -> cityRepository.save(cityMapper.toEntity(cityDTO)));

        log.info("city = " + city.getName());

        return cityMapper.toCityDTO(city);
    }
}