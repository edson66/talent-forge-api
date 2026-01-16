package com.talentForge.api.infrastructure.persistence.repository;

import com.talentForge.api.domain.repository.CandidateRepository;
import com.talentForge.api.domain.repository.RecruiterRepository;
import com.talentForge.api.infrastructure.persistence.entity.Candidate;
import com.talentForge.api.infrastructure.persistence.entity.Recruiter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RecruiterRepositoryImpl implements RecruiterRepository {

    @Autowired
    private JpaRecruiterRepository repository;

    @Override
    public void save(Recruiter recruiter) {
        repository.save(recruiter);
    }

    @Override
    public Optional<Recruiter> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
