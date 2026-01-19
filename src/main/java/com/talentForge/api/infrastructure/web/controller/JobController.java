package com.talentForge.api.infrastructure.web.controller;

import com.talentForge.api.application.service.JobService;
import com.talentForge.api.infrastructure.persistence.entity.Job;
import com.talentForge.api.infrastructure.persistence.entity.Recruiter;
import com.talentForge.api.infrastructure.persistence.entity.User;
import com.talentForge.api.infrastructure.web.dto.request.CreateJobDTO;
import com.talentForge.api.infrastructure.web.dto.request.FullJobDataDTO;
import com.talentForge.api.infrastructure.web.dto.request.UpdateJobDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<FullJobDataDTO> postJob(@RequestBody @Valid CreateJobDTO data, @AuthenticationPrincipal User user,
                                  UriComponentsBuilder builder){

        var job = jobService.saveJob(data,user);


        var uri = builder.path("/job/{id}").buildAndExpand(job.getId()).toUri();

        return ResponseEntity.created(uri).body(new FullJobDataDTO(job));
    }

    @GetMapping
    public ResponseEntity<Page<FullJobDataDTO>> listJobs(@PageableDefault(sort = "createdAt")Pageable pageable){

        var jobs = jobService.listJobs(pageable);

        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullJobDataDTO> findJobById(@PathVariable Long id){
        var job = jobService.findJobById(id);

        return ResponseEntity.ok(new FullJobDataDTO(job));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FullJobDataDTO> updateJob(@PathVariable Long id,
                                    @AuthenticationPrincipal User user,
                                    @RequestBody UpdateJobDTO data){
        var job = jobService.updateJob(id,user,data);

        return ResponseEntity.ok(job);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delJob(@PathVariable Long id,@AuthenticationPrincipal User user){

        jobService.deleteJob(id,user);

        return ResponseEntity.noContent().build();
    }
}
