package com.talentForge.api.domain.repository;

import com.talentForge.api.infrastructure.persistence.entity.Application;

import java.util.Optional;

public interface ApplicationRepository {
    void save(Application application);

    Optional<Application> findById(Long id);

    Optional<Application> findByJobIdOrderByMatchPercentageDesc(Long jobId);
}
