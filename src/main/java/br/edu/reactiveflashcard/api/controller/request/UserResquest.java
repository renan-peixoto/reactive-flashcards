package br.edu.reactiveflashcard.api.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserResquest(
        @JsonProperty("name")
        @NotBlank
        @Size(min = 1, max = 255)
        String name,
        @JsonProperty("email")
        @NotBlank
        @Email
        @Size(min = 1, max = 255)
        String email
) {

    @Builder(toBuilder = true)
    public UserResquest {
    }
}
