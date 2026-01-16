package com.talentForge.api.application.service;

import com.talentForge.api.domain.model.roles.UserRoles;
import com.talentForge.api.domain.repository.CandidateRepository;
import com.talentForge.api.domain.repository.RecruiterRepository;
import com.talentForge.api.domain.repository.UserRepository;
import com.talentForge.api.infrastructure.persistence.entity.Candidate;
import com.talentForge.api.infrastructure.persistence.entity.Recruiter;
import com.talentForge.api.infrastructure.persistence.entity.User;
import com.talentForge.api.infrastructure.web.dto.request.CandidateRegisterDTO;
import com.talentForge.api.infrastructure.web.dto.request.RecruiterResgisterDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void registerCandidate(CandidateRegisterDTO data){
        var user = new User();
        user.setEmail(data.email());
        user.setName(data.nome());
        user.setPassword(encoder.encode(data.senha()));
        user.setRoles(UserRoles.CANDIDATE);

        var candidate = new Candidate();
        candidate.setUser(user);

        candidateRepository.save(candidate);
    }

    @Transactional
    public void registerRecruiter(RecruiterResgisterDTO data){
        var user = new User();
        user.setEmail(data.email());
        user.setName(data.nome());
        user.setPassword(encoder.encode(data.senha()));
        user.setRoles(UserRoles.RECRUITER);

        var recruiter = new Recruiter();
        recruiter.setCompany(data.company());
        recruiter.setUser(user);

        recruiterRepository.save(recruiter);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Não existe um Usuário com esse email!"));


        return user;
    }
}
