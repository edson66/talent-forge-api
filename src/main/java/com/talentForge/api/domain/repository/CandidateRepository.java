package com.talentForge.api.domain.repository;

import com.talentForge.api.infrastructure.persistence.entity.Candidate;

import java.util.Optional;

public interface CandidateRepository {
    void save(Candidate candidate);

    Optional<Candidate> findByEmail(String email);
}
