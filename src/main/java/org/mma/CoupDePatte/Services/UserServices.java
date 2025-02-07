package org.mma.CoupDePatte.Services;

import org.mma.CoupDePatte.Models.Entities.User;
import org.mma.CoupDePatte.Models.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id) {
        return userRepository.getUserById(id);
    }
}
