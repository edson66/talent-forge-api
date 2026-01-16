package com.talentForge.api.infrastructure.persistence.repository;

import com.talentForge.api.domain.repository.CandidateRepository;
import com.talentForge.api.infrastructure.persistence.entity.Candidate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CandidateRepositoryImpl implements CandidateRepository {

    @Autowired
    private JpaCandidateRepository repository;

    @Override
    public void save(Candidate candidate) {
        repository.save(candidate);
    }

    @Override
    public Optional<Candidate> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
