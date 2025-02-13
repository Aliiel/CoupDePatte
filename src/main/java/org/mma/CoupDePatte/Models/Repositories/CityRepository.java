package org.mma.CoupDePatte.Models.Repositories;

import org.mma.CoupDePatte.Models.Entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByName(String name);

    Optional<City> findByNameAndZipCodeIgnoreCase(String name, String zipCode);
}
