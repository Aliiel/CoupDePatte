package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.Entities.Gender;
import org.mma.CoupDePatte.Models.Repositories.GenderRepository;
import org.springframework.stereotype.Service;

@Service
public class GenderServices {
    private final GenderRepository genderRepository;

    public GenderServices(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public Gender getById(long id){
        return genderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type avec ID " + Long.toString(id) + " inconnue"));
    }
}
