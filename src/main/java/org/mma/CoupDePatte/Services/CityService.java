package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.DTO.CityDTO;
import org.mma.CoupDePatte.Models.Entities.City;
import org.mma.CoupDePatte.Models.Mappers.CityMapper;
import org.mma.CoupDePatte.Models.Repositories.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    CityRepository cityRep;
    CityMapper cityMap;

    public CityService(CityRepository cityRepository,CityMapper cityMapper){

        this.cityRep = cityRepository;
        this.cityMap = cityMapper;
    }

    public City getCity(CityDTO cityDTO){
        // renvoie la city sans création si n'existe pas
        return cityRep.findCity(cityDTO.getName(), cityDTO.getZipCode())
                .orElseThrow(() -> new ResourceNotFoundException("Ville " + cityDTO.getName()
                        +", code postal " + cityDTO.getZipCode()+" inconnue"));

    }

    public City getByDTO(CityDTO cityDTO){
        // renvoie la city et la crée si n'existe pas
        return (cityRep.findCity(cityDTO.getName(), cityDTO.getZipCode())).orElse(createCity(cityDTO)) ;

    }

    public City createCity(CityDTO cityDTO){
        City city = new City();
        city.setName(cityDTO.getName());
        city.setZipCode(cityDTO.getZipCode());
        cityRep.save(city);
        return city;
    }
}
