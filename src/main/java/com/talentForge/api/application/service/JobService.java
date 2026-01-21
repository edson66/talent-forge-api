package com.talentForge.api.application.service;

import com.talentForge.api.domain.repository.JobRepository;
import com.talentForge.api.domain.repository.RecruiterRepository;
import com.talentForge.api.infrastructure.persistence.entity.Job;
import com.talentForge.api.infrastructure.persistence.entity.Recruiter;
import com.talentForge.api.infrastructure.persistence.entity.User;
import com.talentForge.api.infrastructure.web.dto.request.CreateJobDTO;
import com.talentForge.api.infrastructure.web.dto.request.FullJobDataDTO;
import com.talentForge.api.infrastructure.web.dto.request.UpdateJobDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;


    public Page<FullJobDataDTO> listJobs(Pageable pageable){
        return jobRepository.findAll(pageable).map(FullJobDataDTO::new);
    }

    @Transactional
    public Job saveJob(CreateJobDTO data, User user){

        var recruiter = recruiterRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Recrutador não encontrado para este usuário."));

        var job = new Job();
        job.setTitle(data.title());
        job.setDescription(data.description());
        job.setRequirements(data.requirements());
        job.setRecruiter(recruiter);

        jobRepository.save(job);

        return job;
    }

    public Job findJobById(Long id){
        return jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada para este id"));
    }

    @Transactional
    public FullJobDataDTO updateJob(Long id, User user, UpdateJobDTO data){
        var job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada para este id"));

        var recruiter = recruiterRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Recrutador não encontrado para este usuário."));

        if (Objects.equals(recruiter.getId(), job.getRecruiter().getId())){
            job.update(data);
            return new FullJobDataDTO(job);
        }else {
            throw new RuntimeException("Você só pode alterar os dados das próprias vagas");
        }
    }

    @Transactional
    public void deleteJob(Long id, User user){
        var job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada para este id"));

        var recruiter = recruiterRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Recrutador não encontrado para este usuário."));

        if (Objects.equals(recruiter.getId(), job.getRecruiter().getId())){
            jobRepository.delete(job);
        }else {
            throw new RuntimeException("Você só pode deletar suas próprias vagas");
        }

    }
}
