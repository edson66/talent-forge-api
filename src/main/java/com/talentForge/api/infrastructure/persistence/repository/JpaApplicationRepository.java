package com.talentForge.api.infrastructure.persistence.repository;

import com.talentForge.api.infrastructure.persistence.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaApplicationRepository extends JpaRepository<Application,Long> {

    Optional<Application> findByJobIdOrderByMatchPercentageDesc(Long jobId);
}
