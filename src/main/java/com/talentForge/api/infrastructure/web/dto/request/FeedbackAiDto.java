package com.talentForge.api.infrastructure.web.dto.request;

import java.util.List;

public record FeedbackAiDto(
        Integer nota,
        String resumo,
        List<String> pontosFortes,
        List<String> pontosFracos,
        String veredito
) {
}
