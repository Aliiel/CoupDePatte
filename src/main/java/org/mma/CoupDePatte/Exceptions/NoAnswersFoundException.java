package org.mma.CoupDePatte.Exceptions;

public class NoAnswersFoundException extends RuntimeException {

    private static final String MESSAGE = "Vous n'avez pas encore de réponses envoyées";
    private final int status;

    public NoAnswersFoundException(int status) {
        super(MESSAGE);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
