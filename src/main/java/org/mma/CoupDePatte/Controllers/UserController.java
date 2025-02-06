package org.mma.CoupDePatte.Controllers;

import lombok.RequiredArgsConstructor;
import org.mma.CoupDePatte.Models.DTO.RegistrationResponse;
import org.mma.CoupDePatte.Models.DTO.UserDTO;
import org.mma.CoupDePatte.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> createUser(@RequestBody UserDTO userDTO) {

        RegistrationResponse registrationResponse = userService.createUser(userDTO);
        return ResponseEntity.ok(registrationResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}
