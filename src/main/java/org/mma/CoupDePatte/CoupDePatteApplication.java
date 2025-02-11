package org.mma.CoupDePatte;

import org.mma.CoupDePatte.Models.Entities.*;
import org.mma.CoupDePatte.Models.Repositories.*;
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

	public static void main(String[] args) {
		SpringApplication.run(CoupDePatteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (genderRep.count() == 0) {
			initializeData();
		}
	}

	private void initializeData() {
		// Crée les enregistrements de la table gender
		Gender gender1 = new Gender(null,"femelle");
		Gender gender2 = new Gender(null,"mâle");
		genderRep.saveAll(List.of(gender1,gender2));

		// Crée les enregistrements de la table specie
		Specie specie1 = new Specie(null,"chat");
		Specie specie2 = new Specie(null,"chien");
		specieRep.saveAll(List.of(specie1,specie2));

		// Crée les enregistrements de la table breed
		Specie spChat = specieRep.findByName("chat");
		Specie spChien = specieRep.findByName("chien");

		Breed breed1 = new Breed(null,"européen",spChat);
		Breed breed2 = new Breed(null,"bâtard",spChien);
		Breed breed3 = new Breed(null,"bengal",spChat);
		Breed breed4 = new Breed(null,"siamois",spChat);
		Breed breed5 = new Breed(null,"main coon",spChat);
		Breed breed6 = new Breed(null,"chartreux",spChat);
		Breed breed7 = new Breed(null,"sphynx",spChat);
		Breed breed8 = new Breed(null,"scottish fold",spChat);
		Breed breed9 = new Breed(null,"persan",spChat);

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
