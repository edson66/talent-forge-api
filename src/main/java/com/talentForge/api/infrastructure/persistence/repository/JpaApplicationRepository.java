package com.talentForge.api.infrastructure.persistence.repository;

import com.talentForge.api.infrastructure.persistence.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaApplicationRepository extends JpaRepository<Application,Long> {
}
