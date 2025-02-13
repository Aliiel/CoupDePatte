package org.mma.CoupDePatte.Exceptions;

public class NoAdvertsFoundException extends RuntimeException{

    private static final String MESSAGE = "Pas d'annonces publiées";
    private final int status;

    public NoAdvertsFoundException(int status) {
        super(MESSAGE);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
