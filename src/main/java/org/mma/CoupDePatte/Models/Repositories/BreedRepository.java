package org.mma.CoupDePatte.Models.Repositories;

import org.mma.CoupDePatte.Models.Entities.Breed;
import org.mma.CoupDePatte.Models.Entities.Specie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

    Optional<Breed> findById(Long id);

    Optional<Breed> findByName(String name);
    ArrayList<Breed> findBySpecie(Specie specie);
}
