package org.mma.CoupDePatte.Models.Repositories;

import org.mma.CoupDePatte.Models.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {

    Optional<Advert> findByIdAndIsActiveTrue(Long id);

    //Annonces "Trouvé"
    ArrayList<Advert> findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundTrue
            (City city, Date eventDate);
    ArrayList<Advert> findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundTrueAndPetInOrderByEventDateDesc
            (City city, Date eventDate, ArrayList<Pet> pets);
    //Annonces "Perdu"
    ArrayList<Advert> findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundFalse
    (City city, Date eventDate);
    ArrayList<Advert> findByCityAndEventDateLessThanAndIsActiveTrueAndIsFoundFalseAndPetInOrderByEventDateDesc
            (City city, Date eventDate,ArrayList<Pet> pets);

}
