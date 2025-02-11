package org.mma.CoupDePatte.Models.Repositories;

import org.mma.CoupDePatte.Models.Entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findById(Long id);

}


