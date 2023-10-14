package br.edu.reactiveflashcard.api.controller.response;

import lombok.Builder;

public record ErrorFieldResponse(
        String name,
        String message
) {

    @Builder(toBuilder = true)
    public ErrorFieldResponse {
    }
}
