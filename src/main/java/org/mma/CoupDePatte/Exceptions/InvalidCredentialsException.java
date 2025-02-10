package org.mma.CoupDePatte.Exceptions;

public class InvalidCredentialsException extends RuntimeException {

    private final int status;

    private static final String MESSAGE = "Les identifiants saisis sont invalides.";

    public InvalidCredentialsException(int status) {
        super(MESSAGE);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
