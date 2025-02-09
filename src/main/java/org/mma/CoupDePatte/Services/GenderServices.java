package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Exceptions.ResourceNotFoundException;
import org.mma.CoupDePatte.Models.Entities.Gender;
import org.mma.CoupDePatte.Models.Repositories.GenderRepository;
import org.springframework.stereotype.Service;

@Service
public class GenderServices {
    GenderRepository genderRep;

    public GenderServices(GenderRepository genderRepository){
        this.genderRep=genderRepository;
    }

    public Gender getById(long id){
        return genderRep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre avec ID " + id + " inconnu"));
    }

    public Gender getByName(String name){
        return genderRep.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Genre avec nom " + name + " non prévu"));
    }
}
