package com.talentForge.api.infrastructure.web.controller;

import com.talentForge.api.application.service.ApplicationService;
import com.talentForge.api.domain.repository.ApplicationRepository;
import com.talentForge.api.domain.service.FileStorageService;
import com.talentForge.api.infrastructure.persistence.entity.User;
import com.talentForge.api.infrastructure.web.dto.response.ApplicationSummaryDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService service;



    @PostMapping(value = "/{jobId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity applyForJob(@PathVariable Long jobId, @RequestParam("resume")MultipartFile file,
                                      @AuthenticationPrincipal User user){

        service.apply(jobId,user,file);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{applicationId}/resume-url")
    public ResponseEntity<String> getUrl(@PathVariable Long applicationId){

        var url = service.getResumeUrl(applicationId);

        return ResponseEntity.ok(url);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationSummaryDTO>> listCandidates(@PathVariable Long jobId,
                                                                      @AuthenticationPrincipal User user) {
        var list = service.listApplicationsByJob(jobId, user);
        return ResponseEntity.ok(list);
    }

}
