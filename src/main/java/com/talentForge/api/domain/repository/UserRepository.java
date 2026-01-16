package com.talentForge.api.domain.repository;

import com.talentForge.api.infrastructure.persistence.entity.User;

import java.util.Optional;


public interface UserRepository {

    Optional<User> findByEmail(String email);
}
