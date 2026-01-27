package com.talentForge.api.domain.repository;

import com.talentForge.api.infrastructure.persistence.entity.Candidate;
import com.talentForge.api.infrastructure.persistence.entity.Recruiter;

import java.util.Optional;

public interface RecruiterRepository {
    void save(Recruiter recruiter);

    Optional<Recruiter> findByUserEmail(String email);
}
