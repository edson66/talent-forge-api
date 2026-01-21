package com.talentForge.api.domain.repository;

import com.talentForge.api.infrastructure.persistence.entity.Application;

public interface ApplicationRepository {
    void save(Application application);
}
