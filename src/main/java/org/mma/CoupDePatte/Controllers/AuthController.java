package org.mma.CoupDePatte.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mma.CoupDePatte.Models.DTO.AuthenticationRequest;
import org.mma.CoupDePatte.Models.DTO.RegistrationRequest;
import org.mma.CoupDePatte.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(userService.authenticate(request));
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) {

        return ResponseEntity.ok(userService.registerUser(request));
    }
}
