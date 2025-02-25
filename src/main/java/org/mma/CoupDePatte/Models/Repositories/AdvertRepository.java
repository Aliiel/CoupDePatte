package org.mma.CoupDePatte.Models.Repositories;

import org.mma.CoupDePatte.Models.Entities.Advert;
import org.mma.CoupDePatte.Models.Entities.City;
import org.mma.CoupDePatte.Models.Entities.Pet;
import org.mma.CoupDePatte.Models.Entities.User;
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
    Optional<Advert> findByIdAndIsActiveFalse(Long id);

    //Annonces "Trouvé"
    ArrayList<Advert> findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundTrueAndPetInOrderByEventDateDesc
            (City city, Date eventDate, ArrayList<Pet> pets);
    //Annonces "Perdu"
    ArrayList<Advert> findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundFalseAndPetInOrderByEventDateDesc
            (City city, Date eventDate,ArrayList<Pet> pets);


    @Query("SELECT DISTINCT a FROM Advert a WHERE a.isFound = true AND a.city.id = :cityId AND a.pet.breed.id = :breedId")
    List<Advert> findUsersByMatchingFoundAdverts(@Param("cityId") Long cityId, @Param("breedId") Long breedId);

    List<Advert> findByUser_Id(Long id);

    @Query("SELECT DISTINCT a.user FROM Advert a WHERE a.isFound = false AND a.city.id = :cityId AND a.pet.breed.id = :breedId")
    List<User> findUsersByMatchingLostAdverts(@Param("cityId") Long cityId, @Param("breedId") Long breedId);


}
