package com.talentForge.api.infrastructure.persistence.repository;

import com.talentForge.api.infrastructure.persistence.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCandidateRepository extends JpaRepository<Candidate,Long> {
    Optional<Candidate> findByUserEmail(String email);
}
