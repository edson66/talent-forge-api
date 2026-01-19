package com.talentForge.api.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateJobDTO(
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotBlank
        String requirements
) {
}
