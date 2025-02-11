package org.mma.CoupDePatte.Models.Repositories;

import jakarta.annotation.Nullable;
import org.mma.CoupDePatte.Models.Entities.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {
    Optional<Pet> findById(Long id);

    ArrayList<Pet> findAll (@Nullable Specification<Pet> var1);
    //Gestion du filtre des annonces avec requêtes dynamiques
}


