package org.mma.CoupDePatte.Exceptions;

public class InvalidCredentialsException extends RuntimeException {

    private static final String MESSAGE = "Les identifiants saisis sont invalides.";
    private final int status;

    public InvalidCredentialsException(int status) {
        super(MESSAGE);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
