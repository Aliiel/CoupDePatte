package org.mma.CoupDePatte.Config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mma.CoupDePatte.Models.Entities.*;
import org.mma.CoupDePatte.Models.Repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final SpecieRepository specieRepository;
    private final BreedRepository breedRepository;
    private final GenderRepository genderRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initData() {

        return args -> {
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_ADMIN"));
            }
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_USER"));
            }

            if (!userRepository.existsByEmail("admin@admin.com")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setEmail("admin@admin.com");
                Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
                admin.setRole(adminRole.orElse(null));
                userRepository.save(admin);
            }

            if (genderRepository.count() == 0) {
                Gender gender1 = new Gender(null,"femelle");
                Gender gender2 = new Gender(null,"mâle");
                genderRepository.saveAll(List.of(gender1, gender2));

                Specie specie1 = new Specie(null,"chat");
                Specie specie2 = new Specie(null,"chien");
                specieRepository.saveAll(List.of(specie1, specie2));

                Specie spChat = specieRepository.findByName("chat");
                Specie spChien = specieRepository.findByName("chien");

                List<Breed> breeds = List.of(
                        new Breed(null, "européen", spChat),
                        new Breed(null, "bâtard", spChien),
                        new Breed(null, "bengal", spChat),
                        new Breed(null, "siamois", spChat),
                        new Breed(null, "main coon", spChat),
                        new Breed(null, "chartreux", spChat),
                        new Breed(null, "sphynx", spChat),
                        new Breed(null, "scottish fold", spChat),
                        new Breed(null, "persan", spChat),
                        new Breed(null, "inconnu", spChien),
                        new Breed(null, "labrador", spChien),
                        new Breed(null, "berger allemand", spChien),
                        new Breed(null, "cocker", spChien),
                        new Breed(null, "setter", spChien),
                        new Breed(null, "pitbull", spChien),
                        new Breed(null, "beagle", spChien),
                        new Breed(null, "rottweiler", spChien),
                        new Breed(null, "dogue allemand", spChien),
                        new Breed(null, "cane corso", spChien),
                        new Breed(null, "husky", spChien)
                );
                breedRepository.saveAll(breeds);
            }
            log.info("✅ Données initiales insérées avec succès !");
        };
    }
}
