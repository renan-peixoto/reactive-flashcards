package br.edu.reactiveflashcard.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.OffsetDateTime;

public record QuestionResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("asked")
        String asked,
        @JsonProperty("askedIn")
        OffsetDateTime askedIn
) {

    @Builder(toBuilder = true)
    public QuestionResponse {
    }
}
