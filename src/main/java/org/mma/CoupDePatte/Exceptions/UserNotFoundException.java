package org.mma.CoupDePatte.Exceptions;

public class UserNotFoundException extends RuntimeException {

    private final int status;

    // Message of the exception
    private static final String MESSAGE = "Utilisateur introuvable";

    public UserNotFoundException(int status) {
        super(MESSAGE);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
