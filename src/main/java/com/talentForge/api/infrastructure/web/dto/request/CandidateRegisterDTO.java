package com.talentForge.api.infrastructure.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CandidateRegisterDTO(

        @NotBlank
        @Email
        String email,
        @NotBlank
        String nome,
        @NotBlank
        String senha
) {
}
