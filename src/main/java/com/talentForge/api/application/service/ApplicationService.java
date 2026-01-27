package com.talentForge.api.application.service;

import com.talentForge.api.domain.repository.ApplicationRepository;
import com.talentForge.api.domain.repository.CandidateRepository;
import com.talentForge.api.domain.repository.JobRepository;
import com.talentForge.api.domain.repository.UserRepository;
import com.talentForge.api.domain.service.FileStorageService;
import com.talentForge.api.domain.service.NotificationService;
import com.talentForge.api.infrastructure.persistence.entity.Application;
import com.talentForge.api.infrastructure.persistence.entity.User;
import com.talentForge.api.infrastructure.web.dto.request.FeedbackAiDTO;
import com.talentForge.api.infrastructure.web.dto.response.ApplicationSummaryDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ApplicationService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private NotificationService notificationService;

    private final ChatClient client;

    public ApplicationService(ChatClient.Builder chatBuilder) {
        this.client = chatBuilder
                .defaultSystem("Você é um assistente de RH especialista em triagem de currículos.")
                .build();
    }

    public Application apply(Long jobId, User candidateUser, MultipartFile resume){
        var job = jobRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada"));

        var candidate = candidateRepository.findByUserEmail(candidateUser.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Candidato não encontrado"));

        String resumeContent = extractTextFromPdf(resume);

        var feedback = aiAnalyze(resumeContent,job.getDescription());

        var filename = fileStorageService.uploadFile(resume);

        var application = new Application();
        application.setJob(job);
        application.setMatchPercentage(feedback.matchPercentage());
        application.setResumeFilename(filename);
        application.setCandidate(candidate);
        application.setAiDto(feedback);

        applicationRepository.save(application);

        if (feedback.matchPercentage() >= 80) {
            String recruiterEmail = job.getRecruiter().getUser().getEmail();
            String subject = "Candidato Promissor: " + candidateUser.getName();
            String body = """
                Olá Recrutador!
                O candidato %s acabou de aplicar para a vaga '%s' e teve um match de %d%%!
                Pontos Fortes: %s
                """.formatted(candidateUser.getName(), job.getTitle(), feedback.matchPercentage(), feedback.technicalSkills());

            notificationService.notify(recruiterEmail, subject, body);
        }else{
            notificationService.notify(
                    candidateUser.getEmail(),
                    "Atualização sobre sua candidatura: " + job.getTitle(),
                    "Olá. Agradecemos seu interesse, mas no momento seu perfil não atingiu os requisitos mínimos..."
            );
        }

        return application;
    }

    public List<ApplicationSummaryDTO> listApplicationsByJob(Long jobId, User userRecruiter) {

        var job = jobRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException("Vaga não encontrada"));

        if (!Objects.equals(job.getRecruiter().getUser().getId(), userRecruiter.getId())){
            throw new RuntimeException("Vaga pertencente a outro recrutador!");
        }

        return applicationRepository.findByJobIdOrderByMatchPercentageDesc(jobId)
                .stream()
                .map(app -> new ApplicationSummaryDTO(
                        app.getId(),
                        app.getCandidate().getUser().getName(),
                        app.getCandidate().getUser().getEmail(),
                        app.getMatchPercentage(),
                        app.getStatusApplication().name()
                ))
                .toList();
    }

    private String extractTextFromPdf(MultipartFile resume){
        try{

            TikaDocumentReader documentReader = new TikaDocumentReader(new InputStreamResource(resume.getInputStream()));

            return documentReader.get().getFirst().getFormattedContent();
        }catch (IOException e) {
            throw new RuntimeException("Falha ao ler PDF",e);
        }
    }

    public FeedbackAiDTO aiAnalyze(String resumeText,String jobDesc){
        String prompt = """
            Analise o seguinte currículo para a vaga descrita.
            Responda APENAS um JSON estrito com os campos: matchPercentage (0-100), technicalSkills, missingSkills e reasoning.
            
            VAGA: %s
            CURRÍCULO: %s
            """.formatted(jobDesc, resumeText);

        return client.prompt()
                .user(prompt)
                .call()
                .entity(FeedbackAiDTO.class);
    }

    public Application findApplicationById(Long applicationId) {

        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Candidatura não encontrada"));
    }

    public String getResumeUrl(Long applicationId) {

        var application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Candidatura não encontrada"));

        return fileStorageService.getFileUrl(application.getResumeFilename());
    }
}
