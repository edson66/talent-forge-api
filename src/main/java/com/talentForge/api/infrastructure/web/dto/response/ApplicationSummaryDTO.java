package com.talentForge.api.infrastructure.web.dto.response;

public record ApplicationSummaryDTO(
        Long applicationId,
        String candidateName,
        String candidateEmail,
        Integer matchPercentage,
        String status
) {
}
