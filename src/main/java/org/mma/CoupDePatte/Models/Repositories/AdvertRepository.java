package org.mma.CoupDePatte.Models.Repositories;

import org.mma.CoupDePatte.Models.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {

    Optional<Advert> findByIdAndIsActiveTrue(Long id);

    //Annonces "Trouvé"

    ArrayList<Advert> findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundTrue
        (City city, Date eventDate);

    ArrayList<Advert> findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundTrueAndPetIn
        (City city, Date eventDate, ArrayList<Pet> pets);
    //Annonces "Perdu"

    ArrayList<Advert> findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundFalse
    (City city, Date eventDate);
    ArrayList<Advert> findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundFalseAndPetIn
        (City city, Date eventDate,ArrayList<Pet> pets);

    @Query("SELECT DISTINCT a.user FROM Advert a WHERE a.isFound = true AND a.city.id = :cityId AND a.pet.breed.id = :breedId")
    List<User> findUsersByMatchingFoundAdverts(@Param("cityId") Long cityId, @Param("breedId") Long breedId);




}
