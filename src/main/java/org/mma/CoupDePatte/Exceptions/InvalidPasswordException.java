package org.mma.CoupDePatte.Exceptions;

public class InvalidPasswordException extends RuntimeException {

    private static final String MESSAGE = "Le mot de passe ne respecte pas les exigences de sécurité. " +
            "Il doit contenir au moins 8 caractères dont une majuscule, un chiffre et un caractère spécial.";
    private final int status;

    public InvalidPasswordException(int status) {
        super(MESSAGE);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
