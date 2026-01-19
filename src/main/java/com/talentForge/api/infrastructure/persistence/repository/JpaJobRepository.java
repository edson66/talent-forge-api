package com.talentForge.api.infrastructure.persistence.repository;

import com.talentForge.api.infrastructure.persistence.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaJobRepository extends JpaRepository<Job,Long> {

}
