package com.talentForge.api.infrastructure.persistence.repository;

import com.talentForge.api.infrastructure.persistence.entity.Candidate;
import com.talentForge.api.infrastructure.persistence.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRecruiterRepository extends JpaRepository<Recruiter,Long> {
    Optional<Recruiter> findByUserEmail(String email);
}
