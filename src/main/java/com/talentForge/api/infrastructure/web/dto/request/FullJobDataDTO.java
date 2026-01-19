package com.talentForge.api.infrastructure.web.dto.request;


import com.talentForge.api.infrastructure.persistence.entity.Job;

public record FullJobDataDTO(
        Long id,
        String title,
        String description,
        String requirements,
        Long idRecruiter
) {
    public FullJobDataDTO(Job job) {
        this(job.getId(), job.getTitle(), job.getDescription(), job.getRequirements(), job.getRecruiter().getId());
    }
}
