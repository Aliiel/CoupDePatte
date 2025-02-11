package org.mma.CoupDePatte.Services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mma.CoupDePatte.Exceptions.*;
import org.mma.CoupDePatte.Models.DTO.*;
import org.mma.CoupDePatte.Models.Entities.City;
import org.mma.CoupDePatte.Models.Entities.Role;
import org.mma.CoupDePatte.Models.Entities.User;
import org.mma.CoupDePatte.Models.Mappers.CityMapper;
import org.mma.CoupDePatte.Models.Mappers.UserMapper;
import org.mma.CoupDePatte.Models.Repositories.CityRepository;
import org.mma.CoupDePatte.Models.Repositories.RoleRepository;
import org.mma.CoupDePatte.Models.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CityRepository cityRepository;
    private final UserMapper userMapper;
    private final CityMapper cityMapper;
    private final RoleRepository roleRepository;


    // méthode pour créer un utilisateur à partir de username, password et email
    public RegistrationResponse registerUser(RegistrationRequest registrationRequest) {

        Optional<User> existingUser = userRepository.findByEmail(registrationRequest.email());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(HttpStatus.CONFLICT.value());
        }

        if (!isPasswordStrong(registrationRequest.password())) {
            throw new InvalidPasswordException(HttpStatus.BAD_REQUEST.value());
        }

        User user = new User();
        user.setUsername(registrationRequest.username());
        user.setEmail(registrationRequest.email());
        user.setPassword(passwordEncoder.encode(registrationRequest.password()));
        Optional<Role> role = roleRepository.findByName("USER");
        role.ifPresent(user::setRole);
        userRepository.save(user);
        final String token = jwtService.generateToken(user);

        return new RegistrationResponse(token, user.getUsername(), user.getEmail(), user.getRole().getName());
    }


    public UserDTO updateUser(Long id, UserDTO userDTO) {

        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {

            CityDTO cityDTO = userDTO.getCity();
            log.info("city recup de la saisie : " + cityDTO.getName() + " - " + cityDTO.getZipCode());

            Optional<City> existingCity = cityRepository.findCity(cityDTO.getName(), cityDTO.getZipCode());

            City city = existingCity.orElseGet(() -> cityRepository.save(cityMapper.toEntity(cityDTO)));

            existingUser.get().setCity(city);
            existingUser.get().setLastName(userDTO.getLastName());


            User userUpdated = userRepository.save(existingUser.get());

            return userMapper.toUserDTO(userUpdated);

        } else {

            throw new UserNotFoundException(HttpStatus.NOT_FOUND.value());

        }
    }


    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDTO)
                .toList();
    }


    public User getByEmail(String email){
         User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("Utilisateur avec email " + email + " inconnu"));
                return user;
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        try {
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()
            );
            User user = userRepository.findByUsername(request.username()).orElseThrow(() -> new UsernameNotFoundException("Pas d'utilisateur enregistré avec ce username"));
            authenticationManager.authenticate(authenticationToken);
            final String token = jwtService.generateToken(user);
            return new AuthenticationResponse(token);

        } catch (AuthenticationException e) {

            throw new InvalidCredentialsException(HttpStatus.UNAUTHORIZED.value());
        }
    }


    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(HttpStatus.NOT_FOUND.value());
        }
        userRepository.deleteById(id);
    }


    // méthode de vérification de force du mdp
    private boolean isPasswordStrong(String password) {

        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";

        return password.matches(regex);

    }


    public User getById(Long id) {
        return userRepository.getUserById(id);
    }
}
