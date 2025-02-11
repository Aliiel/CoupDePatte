package org.mma.CoupDePatte;

import org.mma.CoupDePatte.Models.Entities.Breed;
import org.mma.CoupDePatte.Models.Entities.Gender;
import org.mma.CoupDePatte.Models.Entities.Role;
import org.mma.CoupDePatte.Models.Entities.Specie;
import org.mma.CoupDePatte.Models.Repositories.BreedRepository;
import org.mma.CoupDePatte.Models.Repositories.GenderRepository;
import org.mma.CoupDePatte.Models.Repositories.RoleRepository;
import org.mma.CoupDePatte.Models.Repositories.SpecieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CoupDePatteApplication implements CommandLineRunner {
	GenderRepository genderRep;
	SpecieRepository specieRep;
	BreedRepository breedRep;

	public CoupDePatteApplication(GenderRepository genderRepository,
								  SpecieRepository specieRepository, BreedRepository breedRepository){
		this.genderRep = genderRepository;
		this.specieRep = specieRepository;
		this.breedRep = breedRepository;
	}

    public CoupDePatteApplication(GenderRepository genderRepository, RoleRepository roleRepository,
                                  SpecieRepository specieRepository, BreedRepository breedRepository) {
        this.genderRep = genderRepository;
        this.specieRep = specieRepository;
        this.breedRep = breedRepository;
        this.roleRep = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CoupDePatteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (genderRep.count() == 0) {
            initializeData();
        }
    }

		// Crée les enregistrements de la table specie
		Specie specie1 = new Specie(null,"chat");
		Specie specie2 = new Specie(null,"chien");
		specieRep.saveAll(List.of(specie1,specie2));

        // Crée les enregistrements de la table specie
        Specie specie1 = new Specie(null, "chat");
        Specie specie2 = new Specie(null, "chien");
        specieRep.saveAll(List.of(specie1, specie2));

        // Crée les enregistrements de la table breed
        Specie spChat = specieRep.findByName("chat");
        Specie spChien = specieRep.findByName("chien");

		Breed breed10 = new Breed(null,"inconnu",spChien);
		Breed breed12 = new Breed(null,"labrador",spChien);
		Breed breed13 = new Breed(null,"berger allemand",spChien);
		Breed breed14 = new Breed(null,"cocker",spChien);
		Breed breed15 = new Breed(null,"setter",spChien);
		Breed breed16 = new Breed(null,"pitbull",spChien);
		Breed breed17 = new Breed(null,"beagle",spChien);
		Breed breed18 = new Breed(null,"rottweiler",spChien);
		Breed breed19 = new Breed(null,"dugue allemand",spChien);
		Breed breed20 = new Breed(null,"cane corso",spChien);
		Breed breed21 = new Breed(null,"husky",spChien);
		Breed breed11 = new Breed(null,"teckel",spChien);

		breedRep.saveAll(List.of(breed1,breed2,breed3,breed4,breed5,breed6,breed7,breed8,breed9,breed10,breed11));
		breedRep.saveAll(List.of(breed12,breed13,breed14,breed15,breed16,breed17,breed18,breed19,breed20,breed2,breed21));
		System.out.println("✅ Données initiales insérées avec succès !");
	}


}
