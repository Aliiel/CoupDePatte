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
public class CoupDePatteApplication  {

    public static void main(String[] args) {
        SpringApplication.run(CoupDePatteApplication.class, args);
    }

}
