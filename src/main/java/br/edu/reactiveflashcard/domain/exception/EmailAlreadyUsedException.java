package br.edu.reactiveflashcard.domain.exception;

public class EmailAlreadyUsedException extends ReactiveFlashcardsException {
    public EmailAlreadyUsedException(String message) {
        super( message );
    }
}
