package org.mma.CoupDePatte.Services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mma.CoupDePatte.Exceptions.InvalidPasswordException;
import org.mma.CoupDePatte.Exceptions.UserAlreadyExistsException;
import org.mma.CoupDePatte.Models.DTO.AuthenticationRequest;
import org.mma.CoupDePatte.Models.DTO.AuthenticationResponse;
import org.mma.CoupDePatte.Models.DTO.CityDTO;
import org.mma.CoupDePatte.Models.DTO.UserDTO;
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



    public UserDTO createUser(UserDTO userDTO) {

        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(HttpStatus.CONFLICT.value());
        }

        if (!isPasswordStrong(userDTO.getPassword())) {
            throw new InvalidPasswordException(HttpStatus.BAD_REQUEST.value());
        }

        CityDTO cityDTO = userDTO.getCity();
        System.out.println("city recup de la saisie : " + cityDTO.getName() + " - " + cityDTO.getZipCode());

        Optional<City> existingCity = cityRepository.findCity(cityDTO.getName(), cityDTO.getZipCode());

        City city = existingCity.orElseGet(() -> cityRepository.save(cityMapper.toEntity(cityDTO)));

        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<Role> role = roleRepository.findByName("USER");
        if (role.isPresent()) {
            user.setRole(role.get());
        }

        user.setCity(city);

        userRepository.save(user);

        return userMapper.toUserDTO(user);
    }


    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDTO)
                .toList();
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

            throw new RuntimeException("Identifiants invalides");
        }
    }


    private boolean isPasswordStrong(String password) {

        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";

        return password.matches(regex);
    }
}
