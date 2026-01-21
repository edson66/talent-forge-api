package com.talentForge.api.application.service;

import com.talentForge.api.domain.repository.ApplicationRepository;
import com.talentForge.api.domain.repository.CandidateRepository;
import com.talentForge.api.domain.repository.JobRepository;
import com.talentForge.api.domain.repository.UserRepository;
import com.talentForge.api.infrastructure.persistence.entity.Application;
import com.talentForge.api.infrastructure.persistence.entity.User;
import com.talentForge.api.infrastructure.web.dto.request.FeedbackAiDTO;
import com.talentForge.api.infrastructure.web.dto.response.ApplicationFeedbackDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ApplicationService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public Application apply(Long jobId, User cadidateUser, MultipartFile resume){
        var job = jobRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada"));

        var candidate = candidateRepository.findByEmail(cadidateUser.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Candidato não encontrado"));

        String resumeContent = extractTextFromPdf(resume);

        var feedback = aiAnalyze(resumeContent,job.getDescription());

        var application = new Application();
        application.setJob(job);
        application.setMatchPercentage(feedback.matchPercentage());
        application.setResumeUrl();
        application.setCandidate(candidate);
        application.setAiDto(new FeedbackAiDTO(feedback.matchPercentage(), feedback.technicalSkills(), feedback.missingSkills(), feedback.reasoning()));

        applicationRepository.save(application);

        return application;
    }

    private String extractTextFromPdf(MultipartFile resume){
        try{

            TikaDocumentReader documentReader = new TikaDocumentReader(new InputStreamResource(resume.getInputStream()));

            return documentReader.get().getFirst().getFormattedContent();
        }catch (IOException e) {
            throw new RuntimeException("Falha ao ler PDF",e);
        }
    }

    public ApplicationFeedbackDTO aiAnalyze(String resumeText,String jobDesc){
        String prompt = """
            Analise o seguinte currículo para a vaga descrita.
            Responda APENAS um JSON estrito com os campos: matchPercentage (0-100), technicalSkills, missingSkills e reasoning.
            
            VAGA: %s
            CURRÍCULO: %s
            """.formatted(jobDesc, resumeText);

        return new ApplicationFeedbackDTO(0,"","","");
    }
}
