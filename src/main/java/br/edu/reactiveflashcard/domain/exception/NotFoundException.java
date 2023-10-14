package br.edu.reactiveflashcard.domain.exception;

public class NotFoundException extends ReactiveFlashcardsException {
    public NotFoundException(String message) {
        super( message );
    }
}
