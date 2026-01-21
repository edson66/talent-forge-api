package com.talentForge.api.infrastructure.web.dto.response;

public record ApplicationFeedbackDTO(
        Integer matchPercentage,
        String technicalSkills,
        String missingSkills,
        String reasoning
) {
}
