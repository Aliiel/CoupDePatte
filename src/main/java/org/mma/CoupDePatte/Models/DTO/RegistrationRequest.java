package org.mma.CoupDePatte.Models.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegistrationRequest(
        @NotNull(message = "Veuillez préciser votre username") String username,
        @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[\\W_]).{8,}$",
                message = "Votre mot de passe doit contenir au moins 8 caractères dont " +
                        "une majuscule, un chiffre et un caractère spécial")
        String password,
        @Email(message = "Votre email doit être valide") String email) {
}
