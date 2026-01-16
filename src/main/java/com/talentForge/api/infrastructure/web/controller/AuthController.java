package com.talentForge.api.infrastructure.web.controller;

import com.talentForge.api.application.service.AuthService;
import com.talentForge.api.infrastructure.web.dto.request.CandidateRegisterDTO;
import com.talentForge.api.infrastructure.web.dto.request.RecruiterResgisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/candidate/register")
    public ResponseEntity<Void> resgisterCandidate(@RequestBody @Valid CandidateRegisterDTO data){

        service.registerCandidate(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/recruiter/register")
    public ResponseEntity<Void> resgisterRecruiter(@RequestBody @Valid RecruiterResgisterDTO data){

        service.registerRecruiter(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
