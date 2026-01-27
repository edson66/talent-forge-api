package com.talentForge.api.infrastructure.persistence.repository;

import com.talentForge.api.domain.repository.JobRepository;
import com.talentForge.api.infrastructure.persistence.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JobRepositoryImpl implements JobRepository {

    @Autowired
    private JpaJobRepository repository;

    @Override
    public void save(Job job) {
        repository.save(job);
    }

    @Override
    public void delete(Job job) {
        repository.delete(job);
    }

    @Override
    public Optional<Job> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<Job> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
