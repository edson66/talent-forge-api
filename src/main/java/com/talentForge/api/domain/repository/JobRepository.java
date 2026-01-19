package com.talentForge.api.domain.repository;

import com.talentForge.api.infrastructure.persistence.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JobRepository {

    void save(Job job);

    void delete(Job job);

    Optional<Job> findById(Long id);

    Page<Job> findAll(Pageable pageable);
}
