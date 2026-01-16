package com.talentForge.api.infrastructure.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RecruiterResgisterDTO(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String nome,
        @NotBlank
        String senha,
        @NotBlank
        String company
) {
}
