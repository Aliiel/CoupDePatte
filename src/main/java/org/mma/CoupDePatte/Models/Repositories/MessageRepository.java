package org.mma.CoupDePatte.Models.Repositories;

import org.mma.CoupDePatte.Models.Entities.Message;
import org.mma.CoupDePatte.Models.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT DISTINCT m.user FROM Message m WHERE m.advert.isFound = true AND m.advert.city.id = :cityId AND m.advert.pet.breed.id = :breedId")
    List<User> findUsersByMatchingFoundMessages(@Param("cityId") Long cityId, @Param("breedId") Long breedId);

}
