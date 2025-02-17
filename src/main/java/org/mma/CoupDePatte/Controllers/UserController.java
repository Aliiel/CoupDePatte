package org.mma.CoupDePatte.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mma.CoupDePatte.Models.DTO.AdvertResponseDTO;
import org.mma.CoupDePatte.Models.DTO.AnswerReponseDTO;
import org.mma.CoupDePatte.Models.DTO.UserDTO;
import org.mma.CoupDePatte.Models.Entities.User;
import org.mma.CoupDePatte.Services.AdvertService;
import org.mma.CoupDePatte.Services.AnswerService;
import org.mma.CoupDePatte.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AnswerService answerService;
    private final AdvertService advertService;


    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> partialUpdateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }


    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


    @GetMapping("/adverts")
    public ResponseEntity<List<AdvertResponseDTO>> getAdvertsByUser(@AuthenticationPrincipal User user) {
        Long id = user.getId();
        List<AdvertResponseDTO> adverts = advertService.findAdvertsByUserId(id);
        return ResponseEntity.ok(adverts);
    }


    @GetMapping("/answers")
    public ResponseEntity<List<AnswerReponseDTO>> getAnswersByUser(@AuthenticationPrincipal User user) {

        Long id = user.getId();
        List<AnswerReponseDTO> answers = answerService.getAnswersByUserId(id);
        return ResponseEntity.ok(answers);
    }
}
