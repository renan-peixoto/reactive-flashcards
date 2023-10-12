package br.edu.reactiveflashcard.domain.document;

public record Question(String asked,
                       String answered,
                       String expected) {
}
