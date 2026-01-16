package com.talentForge.api.infrastructure.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RecruiterResgisterDTO(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String name,
        @NotBlank
        String password,
        @NotBlank
        String company
) {
}
