package com.talentForge.api.infrastructure.persistence.repository;

import com.talentForge.api.domain.repository.UserRepository;
import com.talentForge.api.infrastructure.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JpaUserRepository repository;

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
