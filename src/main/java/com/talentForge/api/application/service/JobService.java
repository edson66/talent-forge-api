package com.talentForge.api.application.service;

import com.talentForge.api.domain.repository.JobRepository;
import com.talentForge.api.infrastructure.persistence.entity.Job;
import com.talentForge.api.infrastructure.persistence.entity.Recruiter;
import com.talentForge.api.infrastructure.web.dto.request.CreateJobDTO;
import com.talentForge.api.infrastructure.web.dto.request.FullJobDataDTO;
import com.talentForge.api.infrastructure.web.dto.request.UpdateJobDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Transactional
    public URI saveJob(Job job, UriComponentsBuilder uriComponentsBuilder){
        jobRepository.save(job);

        return uriComponentsBuilder.path("/job/{id}").buildAndExpand(job.getId()).toUri();
    }

    public Page<FullJobDataDTO> listJobs(Pageable pageable){
        return jobRepository.findAll(pageable).map(FullJobDataDTO::new);
    }

    public Job findJobById(Long id){
        return jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada para este id"));
    }

    @Transactional
    public FullJobDataDTO updateJob(Long id, Recruiter recruiter, UpdateJobDTO data){
        var job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada para este id"));

        if (Objects.equals(recruiter.getId(), job.getRecruiter().getId())){
            job.update(data);
            return new FullJobDataDTO(job);
        }else {
            throw new RuntimeException("Você só pode alterar os dados das próprias vagas");
        }
    }

    @Transactional
    public void deleteJob(Long id, Recruiter recruiter){
        var job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada para este id"));

        if (Objects.equals(recruiter.getId(), job.getRecruiter().getId())){
            jobRepository.delete(job);
        }else {
            throw new RuntimeException("Você só pode deletar suas próprias vagas");
        }

    }
}
