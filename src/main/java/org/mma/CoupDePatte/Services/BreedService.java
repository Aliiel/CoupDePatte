package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.Entities.Breed;
import org.mma.CoupDePatte.Models.Repositories.BreedRepository;
import org.springframework.stereotype.Service;

@Service
public class BreedService {
    BreedRepository breedRep;

    public BreedService(BreedRepository breedRepository) {
        this.breedRep = breedRepository;
    }

    public Breed getById(long id) {
        return breedRep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Race avec ID " + Long.toString(id) + " inconnue"));
    }


    public Breed getByName(String name) {
        return breedRep.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Race avec nom " + name + " inconnue"));
    }
}
