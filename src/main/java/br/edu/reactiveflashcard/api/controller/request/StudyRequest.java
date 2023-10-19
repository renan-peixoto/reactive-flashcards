package br.edu.reactiveflashcard.api.controller.request;

import br.edu.reactiveflashcard.core.validation.MongoId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record StudyRequest(
        @MongoId
        @JsonProperty("userId")
        String userId,
        @MongoId
        @JsonProperty("deckId")
        String deckId

) {
    @Builder(toBuilder = true)
    public StudyRequest {
    }
}
