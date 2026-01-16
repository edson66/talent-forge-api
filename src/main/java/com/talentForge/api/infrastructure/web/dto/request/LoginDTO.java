package com.talentForge.api.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
