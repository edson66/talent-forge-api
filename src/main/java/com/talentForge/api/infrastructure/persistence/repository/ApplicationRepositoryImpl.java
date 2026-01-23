package com.talentForge.api.infrastructure.persistence.repository;

import com.talentForge.api.domain.repository.ApplicationRepository;
import com.talentForge.api.infrastructure.persistence.entity.Application;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ApplicationRepositoryImpl implements ApplicationRepository {

    @Autowired
    private JpaApplicationRepository repository;

    @Override
    public void save(Application application) {
        repository.save(application);
    }

    @Override
    public Optional<Application> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Application> findByJobIdOrderByMatchPercentageDesc(Long jobId) {
        return repository.findByJobIdOrderByMatchPercentageDesc(jobId);
    }
}
