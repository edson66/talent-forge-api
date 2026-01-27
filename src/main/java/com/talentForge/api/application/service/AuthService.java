package com.talentForge.api.application.service;

import com.talentForge.api.domain.model.roles.UserRoles;
import com.talentForge.api.domain.repository.CandidateRepository;
import com.talentForge.api.domain.repository.RecruiterRepository;
import com.talentForge.api.domain.repository.UserRepository;
import com.talentForge.api.infrastructure.persistence.entity.Candidate;
import com.talentForge.api.infrastructure.persistence.entity.Recruiter;
import com.talentForge.api.infrastructure.persistence.entity.User;
import com.talentForge.api.infrastructure.web.dto.request.CandidateRegisterDTO;
import com.talentForge.api.infrastructure.web.dto.request.LoginDTO;
import com.talentForge.api.infrastructure.web.dto.request.RecruiterResgisterDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @Autowired
    @Lazy
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Transactional
    public void registerCandidate(CandidateRegisterDTO data){
        var user = new User();
        user.setEmail(data.email());
        user.setName(data.name());
        user.setPassword(encoder.encode(data.password()));
        user.setRole(UserRoles.CANDIDATE);

        var candidate = new Candidate();
        candidate.setUser(user);

        candidateRepository.save(candidate);
    }

    @Transactional
    public void registerRecruiter(RecruiterResgisterDTO data){
        var user = new User();
        user.setEmail(data.email());
        user.setName(data.name());
        user.setPassword(encoder.encode(data.password()));
        user.setRole(UserRoles.RECRUITER);

        var recruiter = new Recruiter();
        recruiter.setCompany(data.company());
        recruiter.setUser(user);

        recruiterRepository.save(recruiter);
    }

    @Transactional
    public String login(LoginDTO data){
        var authenticadedUser = manager.authenticate(new UsernamePasswordAuthenticationToken(data.email(),data.password()));
        var tokenJWT = tokenService.generateToken((User) authenticadedUser.getPrincipal());

        return tokenJWT;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Não existe um Usuário com esse email!"));

        return user;
    }
}
