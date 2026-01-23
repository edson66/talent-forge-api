package com.talentForge.api.infrastructure.persistence.entity;

import com.talentForge.api.domain.model.roles.StatusApplication;
import com.talentForge.api.infrastructure.web.dto.request.FeedbackAiDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id",referencedColumnName = "id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "job_id",referencedColumnName = "id")
    private Job job;

    private String resumeFilename;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "ai_feedback",columnDefinition = "jsonb")
    private FeedbackAiDTO aiDto;

    @Column(name = "status")
    private StatusApplication statusApplication = StatusApplication.RECEIVED;
    private Integer matchPercentage;

    @CreationTimestamp
    @Column(name = "applied_at",nullable = false,updatable = false)
    private LocalDateTime appliedAt;
}
