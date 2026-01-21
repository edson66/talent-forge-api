package com.talentForge.api.infrastructure.web.dto.request;

import java.util.List;

public record FeedbackAiDTO(
        Integer matchPercentage,
        String technicalSkills,
        String missingSkills,
        String reasoning
) {
}
