package org.mma.CoupDePatte.Exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    // Message of the exception
    private static final String MESSAGE =
            "Un utilisateur existe déjà avec cette adresse e-mail. " +
                    "Veuillez vous connecter ou réinitialiser votre mot de passe en cas d'oubli.";
    private final int status;

    public UserAlreadyExistsException(int status) {
        super(MESSAGE);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
