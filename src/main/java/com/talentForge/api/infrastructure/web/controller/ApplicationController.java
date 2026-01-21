package com.talentForge.api.infrastructure.web.controller;

import com.talentForge.api.infrastructure.persistence.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    @PostMapping(value = "/{jobid}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity applyForJob(@PathVariable Long id, @RequestParam("resume")MultipartFile file,
                                      @AuthenticationPrincipal User user){

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
