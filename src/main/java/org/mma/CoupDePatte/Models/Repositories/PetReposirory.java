package org.mma.CoupDePatte.Models.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetReposirory extends JpaRepository<Pet, Long> {
}
