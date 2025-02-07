package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Models.Entities.Specie;
import org.mma.CoupDePatte.Models.Repositories.SpecieRepository;
import org.springframework.stereotype.Service;

@Service
public class SpecieServices {
   SpecieRepository specieRep;

   public SpecieServices(SpecieRepository specieRepository){
       this.specieRep=specieRepository;
   }

   public Specie getByName(String name){
       return specieRep.findByName(name);
   }
}
