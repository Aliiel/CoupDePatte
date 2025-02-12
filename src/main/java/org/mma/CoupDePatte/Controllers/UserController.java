package org.mma.CoupDePatte.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mma.CoupDePatte.Models.DTO.AnswerDTO;
import org.mma.CoupDePatte.Models.DTO.UserDTO;
import org.mma.CoupDePatte.Services.AnswerService;
import org.mma.CoupDePatte.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AnswerService answerService;


    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> partialUpdateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.of(userService.updateUser(id, userDTO));
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


    @GetMapping("/{id}/answers")
    public ResponseEntity<List<AnswerDTO>> getAnswersByUser(@PathVariable Long id) {

        List<AnswerDTO> answers = answerService.getAnswersByUserId(id);

        return ResponseEntity.ok(answers);
    }
}
