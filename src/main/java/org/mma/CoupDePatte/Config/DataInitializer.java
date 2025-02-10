package org.mma.CoupDePatte.Config;

import org.mma.CoupDePatte.Models.Entities.Role;
import org.mma.CoupDePatte.Models.Repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("ADMIN").isEmpty()) {
                roleRepository.save(new Role(null, "ADMIN"));
            }
            if (roleRepository.findByName("USER").isEmpty()) {
                roleRepository.save(new Role(null, "USER"));
            }
        };
    }
}
