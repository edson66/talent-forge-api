package com.talentForge.api.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateJobDTO(
        String title,
        String description,
        String requirements
) {
}
